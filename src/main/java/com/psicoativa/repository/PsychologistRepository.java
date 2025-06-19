package com.psicoativa.repository;

import java.util.List;

import com.psicoativa.model.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.Psychologist;

import jakarta.persistence.Query;

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

    public Psychologist findById(int id){
        Session session = App.sf.openSession();
        Query query = session.createQuery("FROM Psychologist p JOIN UserBase u ON p.id = u.id WHERE p.id = ?1", Psychologist.class);
        query.setParameter(1, id);
        List<Psychologist> psychologists = query.getResultList();
        try {
            return psychologists.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        } finally {
            session.close();
        }
    }

    public List<Psychologist> findByName(String name){
        try (Session session = App.sf.openSession()) {
            name = name.toLowerCase();
            Query query = session.createQuery("FROM Psychologist WHERE LOWER(name) LIKE :name", Psychologist.class);
            query.setParameter("name", '%' + name + '%');
            return query.getResultList();
        }
    }
}
