package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            String query = "create table if not exists ussers (id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ee) {

            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS ussers");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ee) { }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            String update = "insert into ussers (name, lastName, age) values ('"
                    + name + "', '"
                    + lastName + "', '"
                    + age + "')";
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ee) {}
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            stmt.execute("delete from ussers where id ='" + id + "'");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ee) { }
        }
    }

    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            stmt.execute("select * from ussers");
            ResultSet result = stmt.getResultSet();
            if (result.isLast()) {
                return res;
            }
            while(!result.isLast()) {
                result.next();
                res.add(new User(
                        result.getString("name"),
                        result.getString("lastName"),
                        (byte) result.getInt("age")));
            }
            return res;
        } catch (SQLException e) { return new ArrayList<User>();}
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try(Statement stmt = connection.createStatement()) {
            stmt.execute("delete from ussers");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ee) { }
        }
    }
}
