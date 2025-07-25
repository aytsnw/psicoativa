package com.psicoativa.repository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.Appointment;

import jakarta.persistence.Query;

public class AppointmentRepository {
    public void addToDb(Appointment ap) throws DbOperationFailedException{
        Session session = App.sf.openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.persist(ap);
            tr.commit();
        } catch (Exception e) {
            throw new DbOperationFailedException();
        } finally{
            session.close();
        }
    }

    public List<Appointment> findByRange(LocalDate date, int start, int end){
        try (Session session = App.sf.openSession()){
            Query query = session.createQuery("FROM Appointment a WHERE date = :date AND ("+
                                                                "(startTimeId >= :start AND startTimeId <= :end) OR " +
                                                                "(endTimeId >= :start AND endTimeId <= :end) OR " +
                                                                "(startTimeId <= :start AND endTimeId >= :end) OR" +
                                                                "(startTimeId >= :start AND endTimeId <= :end)) AND " +
                                                                "(status = 'active')", Appointment.class);

            query.setParameter("date", date);
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } 
    }

    public void makeCanceled(int appointmentId) throws DbOperationFailedException{
        try (Session session = App.sf.openSession()){
            Transaction tr = session.beginTransaction();
            Query query = session.createQuery("UPDATE Appointment a SET a.status = 'canceled' WHERE a.id = :id");
            query.setParameter("id", appointmentId);
            int rowCount = query.executeUpdate();
            tr.commit();
            if (rowCount == 0) throw new DbOperationFailedException("Appointment not found.");
        }
    }

    public Appointment findById(int id){
        try (Session session = App.sf.openSession()){
            return session.get(Appointment.class, id);
        }
    }
}
