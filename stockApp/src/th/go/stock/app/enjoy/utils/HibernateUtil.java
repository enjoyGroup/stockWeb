package th.go.stock.app.enjoy.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import th.go.stock.app.enjoy.main.ConfigFile;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
		System.out.println("buildSessionFactory.begin");
		
        try {
        	String DB_DRIVER 	= System.getProperty(ConfigFile.getDbDriver());
        	String DB_NAME 		= System.getProperty(ConfigFile.getDbName());
    		String DB_URL 		= System.getProperty(ConfigFile.getDbUrl()).concat("/").concat(DB_NAME).concat("?characterEncoding=UTF-8");
    		String DB_USER 		= System.getProperty(ConfigFile.getDbUser());
    		String DB_PASS 		= System.getProperty(ConfigFile.getDbPass());
    		
        	//for test pdf
//    		String DB_DRIVER 	= "com.mysql.jdbc.Driver";
//        	String DB_NAME 		= "stock";
//    		String DB_URL 		= "jdbc:mysql://localhost:3306/".concat(DB_NAME).concat("?characterEncoding=UTF-8");
//    		String DB_USER 		= "enjoyteam";
//    		String DB_PASS 		= "p@ssw0rd";
        	
        	System.out.println("DB_DRIVER 	:: " + DB_DRIVER);
        	System.out.println("DB_URL 		:: " + DB_URL);
        	System.out.println("DB_USER 	:: " + DB_USER);
        	System.out.println("DB_PASS 	:: " + DB_PASS);
    		
        	// Create the SessionFactory from hibernate.cfg.xml 
            Configuration configuration = new Configuration();
            
            configuration.configure("hibernate.cfg.xml");
            
            configuration.setProperty("hibernate.connection.driver_class"	, DB_DRIVER);
            configuration.setProperty("hibernate.connection.url"			, DB_URL);
            configuration.setProperty("hibernate.connection.username"		, DB_USER);
            configuration.setProperty("hibernate.connection.password"		, DB_PASS);
            
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
