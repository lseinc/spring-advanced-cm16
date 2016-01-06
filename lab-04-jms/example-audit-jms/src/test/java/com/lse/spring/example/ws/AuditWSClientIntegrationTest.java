package com.lse.spring.example.ws;


import com.lse.spring.example.ExampleAuditJmsApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.*;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;

//@RunWith(SpringJUnit4ClassRunner.class) //replaced with JUnit Rules
@WebIntegrationTest
@SpringApplicationConfiguration(classes = ExampleAuditJmsApplication.class)
public class AuditWSClientIntegrationTest {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    WebServiceTemplate wsClient;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Value("${server.port}")
    int port;

    private static boolean wait = true;

    @BeforeClass
    public static final void beforeClass() {
        sleep(3000);
    }

    @Test
    public void testWebServiceCallLive() {
        String message = "Message from JUNIT Test!!!";
        Assert.assertNotNull(wsClient);
        wsClient.setDefaultUri("http://localhost:"+port+"/jaxws/auditWS");
        Audit audit = new Audit();
        audit.setMessage(message);
        AuditResponse response = (AuditResponse)wsClient.marshalSendAndReceive(audit);
        String returnString = response.getReturn();
        Assert.assertEquals("sent",returnString);
    }

    private static final void sleep(int delayMs) {
        try {
            Thread.sleep(delayMs);
        }
        catch(Throwable t) {
            System.err.println(t.getMessage());
        }
    }

}
