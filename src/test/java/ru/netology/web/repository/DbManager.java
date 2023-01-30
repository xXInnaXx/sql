package ru.netology.web.repository;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static final String TRUNCATE_TABLE_USERS = "truncate table users";
    private static final String TRUNCATE_TABLE_CARDS = "truncate table cards";
    private static final String TRUNCATE_TABLE_AUTH_CODES = "truncate table auth_codes";
    private static final String TRUNCATE_TABLE_CARD_TRANSACTIONS = "truncate table card_transactions";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/app";
    public static final String USER = "app";
    public static final String PASSWORD = "pass";
    private static final String SET_FOREIGN_KEY_CHECKS = "set foreign_key_checks = ?";

    public static void cleanDb() {
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD)) {
            runner.execute(conn, SET_FOREIGN_KEY_CHECKS, 0);
            runner.execute(conn, TRUNCATE_TABLE_AUTH_CODES);
            runner.execute(conn, TRUNCATE_TABLE_CARD_TRANSACTIONS);
            runner.execute(conn, TRUNCATE_TABLE_CARDS);
            runner.execute(conn, TRUNCATE_TABLE_USERS);
            runner.execute(conn, SET_FOREIGN_KEY_CHECKS, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getCodeByUser(String user) {
        var runner = new QueryRunner();
        var dataSQL = "select code\n" +
                "from auth_codes\n" +
                "where user_id = (select users.id from users where login = ?)\n" +
                "order by created desc\n" +
                "limit 1;";

        try (var conn = DriverManager.getConnection(
                CONNECTION_STRING,
                USER, PASSWORD)) {
            return runner.query(conn, dataSQL, new ScalarHandler<>(1), user);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}