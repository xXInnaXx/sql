package ru.netology.web.repository;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DbManager {

    public static String getCodeByUser(String user) {
        var runner = new QueryRunner();
        var dataSQL = "select code\n" +
                "from auth_codes\n" +
                "where user_id = (select users.id from users where login = ?)\n" +
                "order by created desc\n" +
                "limit 1;";

        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app",
                "app", "pass")) {
            return runner.query(conn, dataSQL, new ScalarHandler<>(1), user);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}