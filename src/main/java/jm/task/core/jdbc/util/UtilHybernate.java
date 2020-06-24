package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class UtilHybernate {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    @SuppressWarnings("UnusedDeclaration")
    private static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        Properties properties = GetProps.fetch();
        String[] props = {
                "hibernate.dialect",
                "hibernate.connection.driver_class",
                "hibernate.connection.url",
                "hibernate.connection.username",
                "hibernate.connection.password",
                "hibernate.show_sql",
                "hibernate.hbm2ddl.auto"
        };
        new ArrayList<>(Arrays.asList(props)).forEach(propName -> {
            configuration.setProperty(propName, properties.getProperty(propName));
        });

        return configuration;
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = getMySqlConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static class CloseableSession implements AutoCloseable {

        private final Session session;

        public CloseableSession(Session session) {
            this.session = session;
        }

        public Session getSession() {
            return session;
        }

        @Override
        public void close() {
            session.close();
        }
    }

}
