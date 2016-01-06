package com.lse.spring.example;

import com.lse.spring.example.data.AuditRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.*;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExampleAuditJmsApplication.class)
public class ExampleAuditJmsApplicationTests {
	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();


	@Autowired
	AuditRepository dao;

	@Test
	public void contextLoads() {
		long count = 0;
		for (int i = 0; i < 3 ; i++) {
			count = dao.count();
			sleep(1000);
		}
		Assert.assertTrue(count>0);
	}

	private void sleep(int delayMs) {
		try {
			Thread.sleep(delayMs);
		}
		catch(Throwable t) {
			System.err.println(t.getMessage());
		}
	}

}
