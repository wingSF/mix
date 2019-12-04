package com.wing.lynne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 官网地址
 * https://clickhouse.yandex/
 */
public class MainApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:32769/rds_uf_union_order");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show tables");
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            System.out.println(string);
        }


    }
}
