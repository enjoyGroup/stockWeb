package th.go.stock.app.enjoy.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
        try {
        	// Create the SessionFactory from hibernate.cfg.xml 
            Configuration configuration = new Configuration();
            
            configuration.configure("hibernate.cfg.xml");
            
            configuration.setProperty("hibernate.connection.driver_class"	, System.getProperty("ENJOY_DRIVER"));
            configuration.setProperty("hibernate.connection.url"			, System.getProperty("ENJOY_URL"));
            configuration.setProperty("hibernate.connection.username"		, System.getProperty("ENJOY_USER"));
            configuration.setProperty("hibernate.connection.password"		, EnjoyUtils.nullToStr(System.getProperty("ENJOY_PASS")));
            
            System.out.println("Hibernate Configuration loaded");
        	
          //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");
            
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
 
            return sessionFactory;
        }
        catch (Throwable ex) {
        	// Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
	public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
