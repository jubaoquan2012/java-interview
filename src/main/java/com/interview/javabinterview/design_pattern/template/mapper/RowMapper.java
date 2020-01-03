package com.interview.javabinterview.design_pattern.template.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public interface RowMapper<T> {

    T mapRow(ResultSet resultSet, int rowNum) throws Exception;
}
