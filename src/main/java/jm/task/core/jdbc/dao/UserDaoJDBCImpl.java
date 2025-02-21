package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
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
    public static final String DELETE_SQL = """
            DELETE from user
            where id = ?
            """;
    public static final String SAVE_SQL = """
             INSERT INTO user(name, last_name, age)
             VALUES (?,?,?)
            """;
    private static final String GET_ALL_SQL = """
            SELECT * from user
            """;
    private static final String CLEAN_TABLE_SQL = """
            TRUNCATE TABLE user
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
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.execute(CLEAN_TABLE_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
