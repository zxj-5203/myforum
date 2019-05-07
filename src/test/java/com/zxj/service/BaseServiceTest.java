package com.zxj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@Rollback(false) // 防止自动回滚
//指定Spring配置文件，启动Spring容器
@ContextConfiguration("classpath:/spring/applicationContext-*.xml")
//继承Spring测试框架提供的测试基类来启动测试运行器；
public class BaseServiceTest extends AbstractTransactionalTestNGSpringContextTests {
	@Autowired
	public HibernateTemplate hibernateTemplate;
}
