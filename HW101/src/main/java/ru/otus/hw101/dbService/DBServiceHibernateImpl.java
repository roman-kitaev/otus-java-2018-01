package ru.otus.hw101.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw101.base.DBService;
import ru.otus.hw101.base.dataSets.*;
import ru.otus.hw101.dbService.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

/**
 * Created by rel on 24.04.2018.
 */
public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydbtest");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void prepareTables() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMetaData() { //former getLocalStatus
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
        }
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> getAllUsers() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public String getUserName(int id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readNameById(id);
        });
    }

    @Override
    public List<String> getAllNames() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAllNames();
        });
    }

    @Override
    public void addUsers(String... names) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            for(String name : names) {
                dao.save(new UserDataSet(name));
            }
        }
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    @Override
    public void deleteTables() {
        throw new UnsupportedOperationException();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
