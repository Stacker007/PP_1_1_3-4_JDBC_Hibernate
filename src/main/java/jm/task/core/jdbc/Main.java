package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        final UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        User user = new User("Anna", "Politkovskaya", (byte) 44);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем " + user.getName() + " добавлен в базу данных");

        user = new User("Igor", "Gromyko", (byte) 34);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем " + user.getName() + " добавлен в базу данных");

        user = new User("Svetlana", "Teykina", (byte) 20);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем " + user.getName() + " добавлен в базу данных");

        user = new User("Evgeny", "Osadchy", (byte) 65);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.println("User с именем " + user.getName() + " добавлен в базу данных");

        userService.getAllUsers().stream().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
