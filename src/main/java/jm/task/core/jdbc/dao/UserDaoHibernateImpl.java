package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import jm.task.core.jdbc.util.UtilHybernate;
import org.hibernate.Criteria;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        // because of 'create' strategy
        dropUsersTable();
    }

    @Override
    public void dropUsersTable() {
        try (UtilHybernate.CloseableSession session = new UtilHybernate
                .CloseableSession(UtilHybernate.getSessionFactory().openSession())) {
            session.getSession().createQuery("DELETE FROM User").executeUpdate();
        } catch (Exception e) { }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (UtilHybernate.CloseableSession session = new UtilHybernate
                .CloseableSession(UtilHybernate.getSessionFactory().openSession())) {
            session.getSession().save(new User(name, lastName, age));
        } catch (Exception e) { }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        try (UtilHybernate.CloseableSession session = new UtilHybernate
                .CloseableSession(UtilHybernate.getSessionFactory().openSession())) {
            Criteria criteria = session.getSession().createCriteria(User.class);
            return (List<User>) criteria
                    .list();
        } catch (Exception e) { return null;}
    }

    @Override
    public void cleanUsersTable() {

    }
}
