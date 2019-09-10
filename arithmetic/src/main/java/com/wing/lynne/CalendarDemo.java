package com.wing.lynne;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 记录一次，时区配置不当，导致的迷惑
 *
 * question by 萧
 * question address https://github.com/huangsav/testLocalDate1900
 * question description 使用mybatis在插入java.time.LocalDate|java.time.LocalDateTime数据时，发现插入"1900-01-23"，数据库实际写入总与预期不一致
 *
 *
 * 分析插入程序(hikariCP 3.2.0)，最终定位在
 *  LocalDateTime#setNonNullParameter
 *      HikariProxyPreparedStatement#setDate
 *          ClientPreparedStatement#setDate
 *              ClientPreparedQueryBindings#setDate
 *
 * 重要code
 *  this.ddf = TimeUtil.getSimpleDateFormat(this.ddf, "''yyyy-MM-dd''", cal, cal != null ? null : this.session.getServerSession().getDefaultTimeZone());
 *  this.setValue(parameterIndex, this.ddf.format(x), MysqlType.DATE);
 *
 * 上述第一行代码调用时第一个参数可能为null，第二个参数设置给sdf，第三个参数永远为null，第四个参数从服务器(数据库)端获取时区
 * 第二行代码中的this.ddf.format(x)的结果作为sql语句的参数，替换占位符
 *
 * debug发现 x=Date.valueOf("1900-01-23") this.ddf.format(x)="1900-01-22"
 *
 * 至此，问题产生的位置明确
 *
 * 抽象出下面代码，成功复现问题
 *
 * 通过代码执行，确认是由于时区问题导致的
 *
 * 如何解决？
 * 1.更换数据类型，不再使用LocalDate或者LocalDateTime，使用Long毫秒值或者String，可以解决。但往往身不由己。。。
 * 2.重写这些数据的TypeHandle，可以解决该问题，但自己写的敢用么。。。
 * 3.修改服务器的时区，即上海时间
 *
 * 4.最牛逼by 㐂国(我的输入法，尽然没找到第一个字，你们可以试试)
 *   可以在 jdbc:mysql 连接上配置 serverTimezone=CTT
 *   best practice
 *
 *
 */
public class CalendarDemo {

    public static void main(String[] args) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
//        以下俩种时区，可以复现错误
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CST"));

//        以下三种时区设置，数据正常
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CTT"));
//        simpleDateFormat.setTimeZone(TimeZone.getDefault());

        String dateString = "1970-01-23";

        Date date = Date.valueOf(dateString);

        String formatResult = simpleDateFormat.format(date);

        System.out.println(formatResult);

    }
}
