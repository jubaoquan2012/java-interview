package com.interview.javabinterview.design_pattern.template.jdbc;

import com.interview.javabinterview.design_pattern.template.mapper.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public class JdbcTemplate {

    private DataSource dataSource;

    private Connection getConnection() throws Exception {
        return this.dataSource.getConnection();
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public ResultSet executeQuery(PreparedStatement preparedStatement, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i, values[i]);
        }
        return preparedStatement.executeQuery();
    }

    private void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }

    private void closeResult(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    private List<Object> parseResultSet(ResultSet resultSet, RowMapper<?> rowMapper) throws Exception {
        List<Object> list = new ArrayList<>();
        int rowNum = 1;
        while (resultSet.next()) {
            list.add(rowMapper.mapRow(resultSet, rowNum++));
        }
        return list;
    }

    public List<?> executeQuery(String sql, RowMapper<?> rowMapper, Object[] values) throws Exception {
        //1.获取连接
        Connection connection = this.getConnection();
        //2.创建语句集
        PreparedStatement preparedStatement = this.createPreparedStatement(connection, sql);
        //3.执行语句集,并且获取结果集
        ResultSet resultSet = this.executeQuery(preparedStatement, values);
        //4.解析语句集
        List<Object> result = parseResultSet(resultSet, rowMapper);
        //5.关闭语句集
        this.closeResult(resultSet);
        //6.关闭语句集
        this.closeStatement(preparedStatement);
        //7.关闭连接
        this.closeConnection(connection);
        return result;
    }
}
