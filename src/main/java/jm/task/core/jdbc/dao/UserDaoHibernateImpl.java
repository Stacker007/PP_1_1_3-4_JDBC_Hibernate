package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class UserDaoHibernateImpl implements UserDao {


    private final Session session;
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
        session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();


    }

    @Override
    public void dropUsersTable() {
        session.createSQLQuery(DROP_TABLE).executeUpdate();


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.save(new User(name, lastName, age));

    }

    @Override
    public void removeUserById(long id) {
        session.delete(
                session.get(User.class, id)
        );
        session.flush();

    }

    @Override
    public List<User> getAllUsers() {

        return session.createQuery("from User").list();
    }

    @Override
    public void cleanUsersTable() {
        session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();

    }
}
