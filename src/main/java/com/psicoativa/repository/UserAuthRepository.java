package com.psicoativa.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.psicoativa.App;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.model.UserAuth;

import jakarta.persistence.Query;

public class UserAuthRepository {
    public void addToDb(UserAuth user) throws DbOperationFailedException{
        Session session = App.sf.openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.persist(user);
            tr.commit();
        } catch (Exception e) {
            throw new DbOperationFailedException("Already registered in Db: " + "'cpf' or 'email'.");
        } finally{
            session.close();
        }
    }

    public UserAuth findByEmail(String email){
        Session session = App.sf.openSession();
        Query query = session.createQuery("FROM UserAuth WHERE email = ?1", UserAuth.class);
        query.setParameter(1, email);
        List<UserAuth> users = query.getResultList();
        try {
            return users.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new DbOperationFailedException("Email not registered in database.");
        } finally {
            session.close();
        }
    }
}
