package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final List<User> usersForTest = new ArrayList<User>(Arrays.asList(new User[]{
        new User("Ann", "Boleyn", (byte) 42),
        new User("Ann2", "Boleyn", (byte) 49),
        new User("Ann3", "Boleyn", (byte) 44),
        new User("Ann4", "Boleyn", (byte) 46),
    }));
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        usersForTest.forEach((user -> {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем '%s' добавлен в базу данных", user.getName());
            System.out.println("");
        }));
        userService.saveUser("mike", "Bobryshev", (byte) 47);
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> {
            System.out.println(user);
        });
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
