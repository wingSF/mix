package com.wing.lynne;

import com.wing.lynne.mapper.UserMapper;
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

public class MainApplication {

    public static void main(String[] args) throws IOException {

        /**---------------第一步：获取SqlSessionFactory---------------*/
        //xml方式获取SqlSessionFactory
//        String resource = "mybatis-config.xml";
//
//        InputStream resourceStream = Resources.getResourceAsStream(resource);
//
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceStream);


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

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);


        /**---------------第二步：获取SqlSession实例---------------*/
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Integer number1 = sqlSession.selectOne("com.wing.lynne.mapper.UserMapper.findOne");
            System.out.println(number1);
            Integer number2 = sqlSession.selectOne("com.wing.lynne.mapper.UserMapper.findTwo");
            System.out.println(number2);
        }

    }
}
