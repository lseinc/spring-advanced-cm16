package com.lse.spring.example.jms;

import com.lse.spring.example.data.AuditRepository;
import com.lse.spring.example.data.Audit;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AuditMessageListener {

    private static final Logger log = LoggerFactory.getLogger(AuditMessageListener.class);

    public static final String MESSAGE_QUEUE = "example.audit.queue";

    @JmsListener(destination = MESSAGE_QUEUE)
    public void processMessage(String auditMessage) {
        log.info("\n+++ auditMessage: "+auditMessage+"\n");
        store(auditMessage);
    }

    @Autowired
    AuditRepository dao;

    public void store(String auditMessage) {
        try {
            if (auditMessage==null | auditMessage.length()==0) {
                auditMessage = "[empty]";
            }
            Audit audit = new Audit(auditMessage);
            audit = dao.save(audit);
            long count = dao.count();
            log.debug("stored audit={}, count={}", audit, count);
        }
        catch(Throwable t) {
            log.error("AuditMessageListener error: {}\n{}",t.getMessage(),t);
        }
    }
}