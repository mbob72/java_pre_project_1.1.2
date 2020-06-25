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

    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    interface DBAct {
        void execute(Statement stmt) throws SQLException;
    }

    private void executor(DBAct actor) {
        try(Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            actor.execute(stmt);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ee) {}
        }
    }

    public void createUsersTable() {
        executor(stmt -> { stmt.executeUpdate(
      "create table if not exists ussers" +
        " (id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))");
        });
    }

    public void dropUsersTable() {
        executor(stmt -> { stmt.execute("DROP TABLE IF EXISTS ussers"); });
    }

    public void saveUser(String name, String lastName, byte age) {
        executor(stmt -> {
            String update = "insert into ussers (name, lastName, age) values ('"
                    + name + "', '"
                    + lastName + "', '"
                    + age + "')";
            stmt.executeUpdate(update);
        });
    }

    public void removeUserById(long id) {
        executor(stmt -> { stmt.execute("delete from ussers where id ='" + id + "'"); });
    }

    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
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
        executor(stmt -> { stmt.execute("delete from ussers"); });
    }
}
