package com.snowcattle.game.db.service.test.jetty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyServer {

	public static void main(String[] args) {
		//new ClassPathXmlApplicationContext("bean/*.xml");
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/applicationContext-jetty.xml"});
		try {
			new Thread().sleep(100000000l);;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
