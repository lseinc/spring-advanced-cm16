package com.lse.spring.example;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExampleAtmAppApplication.class)
@WebAppConfiguration
public class ExampleAtmAppApplicationTests {
	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(webApplicationContext);
	}

}
