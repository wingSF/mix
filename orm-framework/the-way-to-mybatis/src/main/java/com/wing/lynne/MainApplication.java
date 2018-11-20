package com.wing.lynne;

import com.wing.lynne.constant.Constant;
import com.wing.lynne.mapper.UserMapper;
import com.wing.lynne.po.Province;
import com.wing.lynne.po.User;
import com.wing.lynne.typeHandle.MyStringTypeHandle;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MainApplication {

    public static void main(String[] args) throws IOException {

        /**---------------第一步：获取SqlSessionFactory---------------*/
        //xml方式获取SqlSessionFactory
        String resource = "mybatis-config.xml";

        InputStream resourceStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceStream);


        //非xml形式，自己组织SqlSessionFactory
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
        dataSource.setUsername("root");
        dataSource.setPassword("asdf1111");

        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("dev", jdbcTransactionFactory, dataSource);

        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        //注册自定义TypeHandler
        configuration.getTypeHandlerRegistry().register(MyStringTypeHandle.class);


//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);


        /**---------------第二步：获取SqlSession实例---------------*/
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            //第一种执行mapper中方法的方式
            Integer number1 = sqlSession.selectOne("com.wing.lynne.mapper.UserMapper.findOne");
            System.out.println(number1);
            Integer number2 = sqlSession.selectOne("com.wing.lynne.mapper.UserMapper.findTwo");
            System.out.println(number2);
            //第二种执行mapper中方法的方式
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int one = mapper.findOne();
            int two = mapper.findTwo();
            System.out.println(one);
            System.out.println(two);

        }

        /**---------------第三步：自定义typeHandle测试---------------*/

        //本来想用try with resource实现，结果发现
        //要想使用try with resource，那么resource的对象的作用域只有try块那么大
        //当发生异常的时候，没办法执行rollback方法
        //这个属于try with resource设计的局限性么？（能列入java的改造进程么）
//        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
//
//            User user = new User();
//            user.setName("tom");
//            user.setSex(1);
//            user.setAge(0);
//            user.setBirthday(new Date());
//            user.setPhone("8848");
//            user.setProvince(Province.SX.getProvinceChineseName());
//            user.setRegisterTime(new Date());
//            user.setUpdateTime(new Date());
//
//            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//            int i = mapper.insertUser(user);
//            System.out.println(i == 1 ? "插入成功" : "插入失败");
//
//            sqlSession.commit();
//        }

        SqlSession sqlSession = null;

        try {

            sqlSession = sqlSessionFactory.openSession();

            User user = new User();
            user.setName("jerry");
            user.setSex(0);
            user.setAge(0);
            user.setBirthday(new Date());
            user.setPhone("6636");
            user.setProvince(Province.SX.getProvinceChineseName());
            user.setRegisterTime(new Date());
            user.setUpdateTime(new Date());

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int i = mapper.insertUser(user);
            System.out.println(i == 1 ? "插入成功" : "插入失败");

//            暂时注释掉，保证不会写入重复数据，如果需要写入数据，请开启commit
//            sqlSession.commit();

            String nameResult = mapper.findUserByName(user.getName());

            System.out.println(nameResult);
        } catch (Exception e) {

            e.printStackTrace();
            sqlSession.rollback();

        }


    }
}
