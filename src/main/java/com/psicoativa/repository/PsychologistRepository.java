package com.psicoativa.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.Psychologist;

public class PsychologistRepository {    
        public void addToDb(Psychologist psy) throws DbOperationFailedException{
        Session session = App.sf.openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.persist(psy);
            tr.commit();
        } catch (Exception e) {
            throw new DbOperationFailedException("Already registered in Db: " + "'cpf' or 'email'.");
        } finally {
            session.close();
        }
    }
}
