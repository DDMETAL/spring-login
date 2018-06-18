package test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dmetal.ems.dao.UserDAO;
import com.dmetal.ems.entity.User;
import com.dmetal.ems.service.LoginService;

public class TestCase {
	ApplicationContext ac;
	UserDAO dao;
	@Before
	public void init() {
		String config="springmvc.xml";
		ac=new ClassPathXmlApplicationContext(config);
		dao=ac.getBean("userDAO",UserDAO.class);
	}
	@Test
	//≤‚ ‘ ¡¨Ω”≥ÿ
	public void test1() throws SQLException {
		DataSource ds=ac.getBean("ds",DataSource.class);
		System.out.println(ds.getConnection());
	}
	
	@Test
	//≤‚ ‘ ≥÷æ√≤„
	public void test2() {
		User user=dao.findByUsername("DMETAL");
		System.out.println(user);
	}
	
	@Test
	//≤‚ ‘ “µŒÒ≤„
	public void test3() {
		LoginService ls=ac.getBean("LoginService",LoginService.class);
		User user=ls.checkLogin("DMETAL", "12345");
		System.out.println(user);
		
	}
	@Test
	//≤‚ ‘ findByUsername
	public void test4() {
		User user=dao.findByUsername("DMETAL");
		System.out.println(user);
	}
}
