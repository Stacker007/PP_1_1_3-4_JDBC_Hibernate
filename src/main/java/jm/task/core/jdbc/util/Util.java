package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.ServiceMessage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    private static SessionFactory sessionFactory;

    static {
        loadDriver();
    }

    private Util() {

    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );

        } catch (SQLException e) {
            ServiceMessage.printMessage(e.getMessage());
        }

        return null;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                configuration.setProperty(Environment.URL, PropertiesUtil.get(URL_KEY));
                configuration.setProperty(Environment.USER, PropertiesUtil.get(USER_KEY));
                configuration.setProperty(Environment.PASS, PropertiesUtil.get(PASSWORD_KEY));
                configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty(Environment.SHOW_SQL, "true");
                configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperty(Environment.HBM2DDL_AUTO, "update");
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);            } catch (Exception e) {
                ServiceMessage.printMessage(e.getMessage());

            }
        }
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
