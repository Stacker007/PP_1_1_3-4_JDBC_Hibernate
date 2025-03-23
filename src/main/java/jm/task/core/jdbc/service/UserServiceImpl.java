package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Session session;
    private final UserDao userDao = new UserDaoHibernateImpl(session);


    public void createUsersTable() {
        userDao.createUsersTable();

    }

    public void dropUsersTable() {

        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {

        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);

    }

    public List<User> getAllUsers() {

        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
