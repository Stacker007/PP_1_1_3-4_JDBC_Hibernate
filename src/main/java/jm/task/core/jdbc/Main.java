package jm.task.core.jdbc;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        try (Connection connection = Util.open()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
