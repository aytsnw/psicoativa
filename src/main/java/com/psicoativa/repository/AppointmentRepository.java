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

    public Appointment findByEndHour(LocalDate date, Short time){
        Session session = App.sf.openSession();
        Query query = session.createQuery("FROM Appointment a WHERE date = ?1 AND endHour = ?2", Appointment.class);
        query.setParameter(1, date);
        query.setParameter(2, time);
        List<Appointment> appointments = query.getResultList();
        if (!appointments.isEmpty()){
            return appointments.get(0);
        }
        query = session.createQuery("FROM Appointment a WHERE date = ?1 AND endHour = ?2", Appointment.class);
        query.setParameter(1, date);
        query.setParameter(2, time + 1);
        appointments = query.getResultList();
        if (!appointments.isEmpty()){
            return appointments.get(0);
        }
        return null;
    }
}
