package com.snowcattle.game.db.service.aop;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jwp on 2017/3/22.
 */
public class AOPTest {

    static PersonService personService;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        personService = (PersonService) classPathXmlApplicationContext.getBean("personService");
    }
    @Test
    public void saveTest() {
        personService.save(new Person());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
