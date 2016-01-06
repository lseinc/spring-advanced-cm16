package com.lse.spring.example.atm;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ddlucas for ${PROJECT} project.
 * <p/>
 * Copyright 2016, Lucas Software Engineering, Inc.
 * All rights reserved.
 */
@Configuration
@EnableTransactionManagement
@ImportResource(locations="classpath:spring-minimal.xml")
public class SpringConfiguration {
    /* put special creation and wiring here */
}
