package com.psicoativa;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.Driver;

import com.psicoativa.repository.AppointmentRepository;
import com.psicoativa.repository.ClientRepository;
import com.psicoativa.repository.PsychologistRepository;
import com.psicoativa.repository.UserAuthRepository;
import com.psicoativa.service.AppointmentService;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.LoginService;
import com.psicoativa.service.PsychologistService;
import com.psicoativa.service.RegisterService;
import com.psicoativa.service.UserAuthService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class App implements ServletContextListener{
    public static SessionFactory sf = null;
    public static final String REGISTER_SERVICE_KEY = "register_service";
    public static final String USERAUTH_SERVICE_KEY = "userauth_service";
    public static final String CLIENT_SERVICE_KEY = "client_service";
    public static final String PSYCHOLOGIST_SERVICE_KEY = "pyshcologist_service";
    public static final String LOGIN_SERVICE_KEY = "login_service";
    public static final String APPOINTMENT_SERVICE_KEY = "appointment_service";

    @Override
    public void contextInitialized(ServletContextEvent sce){
        ServletContext context = sce.getServletContext();
        initSingletons(context);

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

    private void initSingletons(ServletContext context){
        ClientRepository cRepo = new ClientRepository();
        PsychologistRepository pRepo = new PsychologistRepository();
        UserAuthRepository uRepo = new UserAuthRepository();
        AppointmentRepository aRepo = new AppointmentRepository();

        UserAuthService uService = new UserAuthService(uRepo);
        ClientService cService = new ClientService(cRepo);
        PsychologistService pService = new PsychologistService(pRepo);
        AppointmentService aService = new AppointmentService(aRepo);
        RegisterService rService = new RegisterService(uService, cService, pService);
        LoginService lService = new LoginService(uService);

        context.setAttribute(REGISTER_SERVICE_KEY, rService);
        context.setAttribute(USERAUTH_SERVICE_KEY, uService);
        context.setAttribute(CLIENT_SERVICE_KEY, cService);
        context.setAttribute(PSYCHOLOGIST_SERVICE_KEY, pService);
        context.setAttribute(LOGIN_SERVICE_KEY, lService);
        context.setAttribute(APPOINTMENT_SERVICE_KEY, aService);
    }
}
