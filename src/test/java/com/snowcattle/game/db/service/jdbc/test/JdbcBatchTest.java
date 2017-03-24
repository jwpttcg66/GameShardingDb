package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.Order;
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
        try {
            OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
            Order order = new Order();
            long id = 1003;
            order.setUserId(id);
            order.setId(id);
            order.setStatus("测试10000");
            order.setSharding_table_index(0);
            mapper.insertEntity(order);
            sqlSession.commit();
        }catch (Exception e){

        }finally {
            sqlSession.close();
        }

    }
}
