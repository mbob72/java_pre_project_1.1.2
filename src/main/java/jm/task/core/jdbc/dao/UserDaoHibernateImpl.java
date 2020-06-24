package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import jm.task.core.jdbc.util.UtilHybernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class UserDaoHibernateImpl implements UserDao {


    private final SessionFactory  sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = UtilHybernate.getSessionFactory();
    }

    interface ExequeteDBAction {
        void execute(Session _session);
    }

    private void executor(ExequeteDBAction callback) {
        Session _session = null;
        Transaction trx = null;

        try(UtilHybernate.CloseableSession session = new UtilHybernate
                .CloseableSession(sessionFactory.openSession())) {
            _session = session.getSession();
            trx = _session.beginTransaction();
            callback.execute(_session);
            trx.commit();
        } catch (Exception e) {
            trx.rollback();
        }
    }

    @Override
    public void createUsersTable() {
        ExequeteDBAction callback = _session -> {
            _session
                .createSQLQuery("create table if not exists User " +
            "(id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))")
                .executeUpdate();
        };
        executor(callback);
    }

    @Override
    public void dropUsersTable() {
        ExequeteDBAction callback = _session -> {
            _session.createSQLQuery("drop TABLE IF EXISTS User").executeUpdate();;
        };
        executor(callback);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        ExequeteDBAction callback = _session -> {
            User user = new User(name, lastName, age);
            _session.save(user);
        };
        executor(callback);
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        try (UtilHybernate.CloseableSession session = new UtilHybernate
                .CloseableSession(sessionFactory.openSession())) {
            Criteria criteria = session.getSession().createCriteria(User.class);
            return (List<User>) criteria
                    .list();
        } catch (Exception e) { return null;}
    }

    @Override
    public void cleanUsersTable() {
        ExequeteDBAction callback = _session -> {
            _session.createSQLQuery("DELETE FROM User").executeUpdate();;
        };
        executor(callback);
    }
}
