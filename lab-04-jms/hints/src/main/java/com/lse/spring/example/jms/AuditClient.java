package com.lse.spring.example.jms;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Queue;

/**
 *
 * This class sends an audit message to the queue to be processed asynchronously.
 *
 */
@Component
public class AuditClient  {
    private static final Logger log = LoggerFactory.getLogger(AuditClient.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("auditQueue")
    private Queue queue;

    @PostConstruct
    public void postConstruct() {
        log.info("+++ queue={}",queue);
    }

    public void audit(String msg) {
        if (msg==null || msg.length()==0) {
            msg = "[empty]";
        }
        log.info("AuditClient.audit called with:  {}", msg);
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }

}
