package com.psicoativa;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.Driver;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class App implements ServletContextListener{
    public static SessionFactory sf = null;

    @Override
    public void contextInitialized(ServletContextEvent sce){
        System.out.println("Context initialized!");
        Driver d = new Driver();
        Configuration config = new Configuration();
        try {
            config.addAnnotatedClass(com.psicoativa.model.Client.class);
            config.addAnnotatedClass(com.psicoativa.model.Psychologist.class);
            config.addAnnotatedClass(com.psicoativa.model.UserAuth.class);
            config.addAnnotatedClass(com.psicoativa.model.Appointment.class);
            config.configure();
            sf = config.buildSessionFactory();
            System.out.println("Hibernate's Session Factory object built!");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        System.out.println("Context finished!");
        sf.close();
    }

}
