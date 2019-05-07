package com.zxj.web;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@Rollback(false) // 防止自动回滚
//指定Spring配置文件，启动Spring容器
@ContextConfiguration("classpath:/spring/applicationContext-*.xml")
//继承Spring测试框架提供的测试基类来启动测试运行器；
public class BaseControllerTest extends AbstractTransactionalTestNGSpringContextTests {
	
	
	
	@Test
	public void getAppbaseUrl() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getSessionUser() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setSessionUser() {
		throw new RuntimeException("Test not implemented");
	}
}
