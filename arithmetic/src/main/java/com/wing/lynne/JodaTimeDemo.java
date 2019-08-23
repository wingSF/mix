package com.wing.lynne;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class JodaTimeDemo {

    public static void main(String[] args) {

        //获取昨天0点
        DateTime dateTime1 = DateTime.now().minusDays(1).withTimeAtStartOfDay();
        //获取今天0点
        DateTime dateTime2 = DateTime.now().withTimeAtStartOfDay();
        //获取上周一0点
        DateTime dateTime3 = DateTime.now().minusWeeks(1).withDayOfWeek(1).withTimeAtStartOfDay();
        //获取上周日0点
        DateTime dateTime4 = DateTime.now().minusWeeks(1).withDayOfWeek(7).withTimeAtStartOfDay();
        //获取上月1号0点
        DateTime dateTime5 = DateTime.now().minusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay();
        //获取上月最后一天0点
        DateTime dateTime6 = DateTime.now().withDayOfMonth(1).minusDays(1).withTimeAtStartOfDay();

        DateTime dateTime7 = new DateTime("2019-08-22T16:00:00.000Z");

        printDateTime(dateTime1);
        printDateTime(dateTime2);
        printDateTime(dateTime3);
        printDateTime(dateTime4);
        printDateTime(dateTime5);
        printDateTime(dateTime6);
        printDateTime(dateTime7);
    }


    private static void printDateTime(DateTime dateTime){
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }
}
