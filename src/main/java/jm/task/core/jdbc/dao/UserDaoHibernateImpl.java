package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Configuration config;
    private SessionFactory sessionFactory;
    private Session session;

    public UserDaoHibernateImpl() {
        this.config = new Configuration();
        config.addAnnotatedClass(User.class);
        config.configure();
        this.sessionFactory = config.buildSessionFactory();
        this.session = sessionFactory.openSession();
    }


    private static final String CLEAN_TABLE_SQL = """
            TRUNCATE TABLE user
            """;
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


    @Override
    public void createUsersTable() {
        session.beginTransaction();
        try {
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                System.out.println("Create table failed");
            }
        }


    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();
        try {
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {

                session.getTransaction().rollback();
                System.out.println("Drop table failed");
            }
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.save(new User(name, lastName, age));

    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        try {
            session.delete(
                    session.get(User.class, id)
            );
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {

                session.getTransaction().rollback();
                System.out.println("Remove User by ID failed");
            }
        }

    }

    @Override
    public List<User> getAllUsers() {

        return session.createQuery("from User").list();
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        try {
            session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                System.out.println("Clean User Table error");
            }
        }

    }
}
