package com.lse.spring.example;

import com.lse.spring.example.jms.AuditClient;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Queue;

/**
 *
 * This class runs after Spring Boot comes up using CommandLineRunner,
 * then it is available via Crash to invoke via audit.
 *
 */
@Component
public class AuditStartup implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AuditStartup.class);

    @Autowired
    AuditClient client;

    @Override
    public void run(String... args) throws Exception {
        StringBuilder message = new StringBuilder();
        if (args!=null && args.length>0) {
            for (String arg : args) {
                message.append(arg);
            }
        }
        log.info("Startup sending message audit client: {}", message.toString());
        client.audit(message.toString());
    }

}
