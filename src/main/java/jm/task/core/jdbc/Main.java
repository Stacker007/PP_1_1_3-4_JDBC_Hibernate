package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            UserService userService = new UserServiceImpl(session);

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
