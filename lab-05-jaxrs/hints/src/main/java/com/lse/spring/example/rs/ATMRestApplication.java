package com.lse.spring.example.rs;


import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class ATMRestApplication extends ResourceConfig {
    private static final Logger log = LoggerFactory.getLogger(ATMRestApplication.class);

    public ATMRestApplication() {
        register(ATMRestService.class);
        register(JacksonFeature.class);
        log.info("+++ ATMRestApplication started...");
    }

}
