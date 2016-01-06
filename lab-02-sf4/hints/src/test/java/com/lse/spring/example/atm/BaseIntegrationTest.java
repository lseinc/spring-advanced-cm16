package com.lse.spring.example.atm;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.rules.*;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = TestSpringConfiguration.class)
@Transactional(noRollbackFor = EmptyResultDataAccessException.class)
@Commit
public abstract class BaseIntegrationTest {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
