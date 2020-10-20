package com.interview.javabinterview.mybatis.jdbc;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

/**
 * Class Description:
 *
 * @author baoquan.Ju
 * Created at  2020/10/20
 */
public class JdbcTest implements Closeable {
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=UTF-8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "341341";
    public Connection connection;

    public void init() throws SQLException {
        //1.创建链接
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Test
    public void jdbcTest() throws SQLException {
        String sql = "select * from users where `name` = ? and 'id' = ?";
        //2. 预编译sql
        PreparedStatement statement = connection.prepareStatement(sql);
        /*
         * 3.设置参数: 第一个参数是索引，表示第几个’?'占位符，从1开始 并不是从0开始。
         *
         * PreparedStatement是如何做到防止sql注入的呢？
         * 在设置参数的时候，setString方法中会给引号加上转译字符，转移操作在数据库端执行，这样就起到了防止sql注入的功能。
         */
        statement.setString(1, "森林");
        statement.setLong(2, 1000001);
        //4. 执行sql
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        System.out.println("===============================");
        statement.setString(1, "依依");//重复使用这个statement 设置参数即可执行
        statement.execute();
        resultSet = statement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        // 释放资源
        resultSet.close();
        statement.close();
    }


    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException sqlException) {

        }
    }
}
