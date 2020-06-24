package jm.task.core.jdbc.util;

import java.io.*;
import java.util.Properties;

public class GetProps {
    public static Properties fetch() {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            return property;

//            String host = property.getProperty("db.host");
//            String login = property.getProperty("db.login");
//            String password = property.getProperty("db.password");
//
//            System.out.println("HOST: " + host
//                    + ", LOGIN: " + login
//                    + ", PASSWORD: " + password);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }

    }
}
