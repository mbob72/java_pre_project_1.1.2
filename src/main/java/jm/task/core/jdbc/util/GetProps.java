package jm.task.core.jdbc.util;
import java.io.*;
import java.util.Properties;

public class GetProps {
    public static Properties fetch() {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/resources/config.properties");
            property.load(fis);

            return property;
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }

    }
}

