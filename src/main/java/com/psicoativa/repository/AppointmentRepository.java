package com.psicoativa.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.Appointment;

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

}
