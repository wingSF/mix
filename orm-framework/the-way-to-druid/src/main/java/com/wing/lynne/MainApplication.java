package com.wing.lynne;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MainApplication {

    public static void main(String[] args) {

        String url = "jdbc:avatica:remote:url=http://120.131.2.233:8082/druid/v2/sql/avatica/";
        Properties connectionProperties = new Properties();
//        String sqlStr = "SELECT client, type_name, pos, title, content_type, tags, count(distinct uuid) AS show_uv FROM dw_ciba_service_ad_show WHERE \"__time\" >= '2020-01-07 00:00:00+0800' AND \"__time\" < '2020-01-08 00:00:00+0800' GROUP BY client, type_name, pos, title, content_type,tags";
        String sqlStr = "SELECT\n" +
                "\tCASE client\n" +
                "\tWHEN 1 THEN\n" +
                "\t'安卓'\n" +
                "\tWHEN 2 THEN\n" +
                "\t'苹果'\n" +
                "\tEND AS client\n" +
                "FROM dw_ciba_service_ad_show\n" +
                "WHERE \"__time\" >= '2020-01-07 00:00:00+0800'\n" +
                "\t\tAND \"__time\" < '2020-01-08 00:00:00+0800'\n" +
                "GROUP BY  id, client, type_name, pos, title, content_type, \"from\",tags limit 1";


        sqlStr = new String(sqlStr.getBytes(), Charset.forName("iso-8859-1"));
        try {
            Connection connection = DriverManager.getConnection(url,connectionProperties);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStr);
            while (resultSet.next()) {
                System.out.println(" druid rs is " + resultSet.getObject(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
