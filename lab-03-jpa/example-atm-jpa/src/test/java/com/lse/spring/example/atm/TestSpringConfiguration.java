package com.lse.spring.example.atm;

import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by ddlucas for ${PROJECT} project.
 * <p/>
 * Copyright 2016, Lucas Software Engineering, Inc.
 * All rights reserved.
 */
@Configuration
@Import(SpringConfiguration.class)
@Profile("example-atm")
public class TestSpringConfiguration {
    /* put test specfic config here */
}
