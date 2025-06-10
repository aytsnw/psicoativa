package com.psicoativa.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.Client;

import jakarta.persistence.Query;

public class ClientRepository {
    public void addToDb(Client client) throws DbOperationFailedException{
        Session session = App.sf.openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.persist(client);
            tr.commit();
        } catch (Exception e) {
            throw new DbOperationFailedException("Already registered in Db: " + "'cpf' or 'email'.");
        } finally{
            session.close();
        }
    }

    public Client findById(int id){
        Session session = App.sf.openSession();
        Query query = session.createQuery("FROM Client WHERE id = ?1", Client.class);
        query.setParameter(1, id);
        List<Client> clients = query.getResultList();
        try {
            return clients.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new DbOperationFailedException("Id not registered in database.");
        } finally {
            session.close();
        }
    }
}
