package com.interview.javabinterview.mybatis.mybatis;

import com.interview.javabinterview.mybatis.mybatis.entity.Blog;
import com.interview.javabinterview.mybatis.mybatis.mapper.BlogMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class Description:
 *
 * @author baoquan.Ju
 * Created at  2020/10/20
 */
public class MyBatisTest {

    /***
     * SqlSessionFactory:
     *  是MyBatis的核心类，它是单个数据库映射关系经过编译后的内存镜像。
     *  主要功能是提供用于操作数据库的SqlSession。
     *
     *  SqlSession:
     *  是MyBatis的关键对象，是持久化操作的独享，类似于jdbc中的Connection，
     *  它是应用程序与持久层之间执行交互操作的一个单线程对象。
     *  SqlSession对象包含全部的以数据库为背景的SQL操作方法，底层封装jdbc连接，
     *  可以用SqlSession实例来直接执行被映射的SQL语句。
     *
     *  注意:SqlSession应该是线程私有的，因为它不具备线程安全性。
     */

    private SqlSessionFactory sqlSessionFactory;

    public void initSqlSessionFactory() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
    }

    private Blog query_1(long id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.selectOne("ocom.interview.javabinterview.mybatis.mybatis.BlogMapper.selectBlog", id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }

    public Blog query_2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            return (Blog) mapper.selectBlog(101);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }
}

