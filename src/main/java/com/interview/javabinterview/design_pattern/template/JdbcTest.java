package com.interview.javabinterview.design_pattern.template;

import com.interview.javabinterview.design_pattern.template.entity.Member;
import com.interview.javabinterview.design_pattern.template.jdbc.JdbcTemplate;
import com.interview.javabinterview.design_pattern.template.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public class JdbcTest {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<?> query() throws Exception {
        String sql = "select * from t_member";
        return jdbcTemplate.executeQuery(sql, new RowMapper<Member>() {

            @Override
            public Member mapRow(ResultSet resultSet, int rowNum) throws Exception {
                Member m = new Member();
                m.setUserName(resultSet.getString("username"));
                m.setPassWord(resultSet.getString("passWord"));
                return m;
            }
        }, null);
    }
}
