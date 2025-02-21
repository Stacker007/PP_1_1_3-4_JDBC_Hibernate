package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    public static final String CREATE_TABLE_SQL = """
            create table user
            (
                id        bigint      not null
                    primary key,
                name      varchar(32) null,
                last_name varchar(32) null,
                age       int         null
            );
            """;
    public static final String DROP_TABLE = """
            DROP TABLE user_schema.user
            """;
    public static final String DELETE_SQL = """
            DELETE from user_schema.user
            where id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO user_schema.user( name, last_name, age) 
            VALUES (?,?,?)
            """;

    public UserDaoJDBCImpl() {

    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
