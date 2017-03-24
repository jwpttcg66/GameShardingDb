package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jwp on 2017/3/24.
 */
public class JdbcBatchTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) classPathXmlApplicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
    }
}
