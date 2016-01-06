package com.lse.spring.example.rs;

import com.lse.spring.example.ExampleAtmAppApplication;
import com.lse.spring.example.atm.ATM;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExampleAtmAppApplication.class)
@WebIntegrationTest
public class ATMRestServiceTest {
    public static final String ACCOUNT_NUMBER = "1234567890";
    
    @Autowired
    ATMRestApplication atmRestApplication;

    @Autowired
    RestTemplate restClient;

    @Value("${server.port}")
    int port;

    @Autowired
    ATM atm;

    String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:"+port+"/rest/atm/";
    }

    @AfterClass
    public static void afterClass() {
//        try {
//            Thread.sleep(120000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testHealth() throws Exception {
        double balance = atm.getAccountBalance(ACCOUNT_NUMBER);
        Assert.assertTrue(balance > 0.0);
        Object result = restClient.getForObject(baseUrl+"/health", String.class);
        Assert.assertNotNull(result);    }
}
