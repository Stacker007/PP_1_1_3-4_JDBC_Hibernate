package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.ServiceMessage;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public static final String CREATE_TABLE_SQL = """
            create table IF NOT EXISTS user
            (
                id        bigint      not null AUTO_INCREMENT
                    primary key,
                name      varchar(32) null,
                last_name varchar(32) null,
                age       int         null
            );
            """;
    public static final String DROP_TABLE = """
            DROP TABLE IF EXISTS user
            """;
    private static final String CLEAN_TABLE_SQL = """
            TRUNCATE TABLE user
            """;
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    @Transactional
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            ServiceMessage.printMessage(e.getMessage());
        }


    }

    @Override
    @Transactional
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        try {
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            ServiceMessage.printMessage(e.getMessage());
        }


    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();

            }
            ServiceMessage.printMessage(e.getMessage());
        }

    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                session.flush();
                session.getTransaction().commit();
            } else {
                throw new HibernateException("User not found");
            }
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            ServiceMessage.printMessage(e.getMessage());
        }

    }

    @Override
    @Transactional
    public List<User> getAllUsers() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            List fromUser = session.createQuery("from User").list();
            session.getTransaction().commit();
            return fromUser;
        } catch (HibernateException e) {
            ServiceMessage.printMessage(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        try {
            session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            ServiceMessage.printMessage(e.getMessage());
        }

    }
}
