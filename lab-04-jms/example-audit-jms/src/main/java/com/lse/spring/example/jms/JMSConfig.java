package com.lse.spring.example.jms;

import com.lse.spring.example.jms.AuditMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.*;

import javax.jms.Queue;

@Configuration
public class JMSConfig {
    @Bean
    public ActiveMQConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory amqFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false&broker.useJmx=false");
        amqFactory.setSendTimeout(30000); //example of a factory setting
        amqFactory.setMaxThreadPoolSize(10);
        return amqFactory;
    }

    @Bean
    public Queue auditQueue() {
        return new ActiveMQQueue(AuditMessageListener.MESSAGE_QUEUE);
    }
}
