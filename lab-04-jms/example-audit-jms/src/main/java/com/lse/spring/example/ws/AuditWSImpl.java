package com.lse.spring.example.ws;

import com.lse.spring.example.jms.AuditClient;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import javax.jws.*;
import javax.xml.ws.WebServiceContext;

@Configurable
@WebService
public class AuditWSImpl implements AuditWS {
    private static final Logger log = LoggerFactory.getLogger(AuditWSImpl.class);

    @Autowired
    AuditClient auditClient;

    @Resource
    protected WebServiceContext wsContext;

    @PostConstruct
    public void postConstruct() {
        log.info("AuditWSImpl is loaded! auditClient={}",auditClient);
    }

    public String audit(String message) {
        log.debug("AuditWS.audit received message: {}",message);
        auditClient.audit(message);
        return "sent";
    }
}
