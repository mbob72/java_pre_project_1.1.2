package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Util {
    private static Connection connection = null;
    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            Properties properties = GetProps.fetch();
            String[] props = {
                    "db.type",
                    "db.host",
                    "db.port",
                    "db.name",
                    "db.login",
                    "db.password",
                    "bd.timezone",
            };
            new ArrayList<>(Arrays.asList(props)).forEach(propName -> {
                url.append(properties.getProperty(propName));
            });

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            connection = getMysqlConnection();
        }
        return connection;
    }
}
