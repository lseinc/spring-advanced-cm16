package com.lse.spring.example.data;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lse.spring.example")
public class JPAConfig {
    @Value("${spring.datasource.driverClassName}")
    String dsDriverClassName;
    @Value("${spring.datasource.url}")
    String dsUrl;
    @Value("${spring.datasource.username}")
    String dsUsername;
    @Value("${spring.datasource.password}")
    String dsPassword;


    @Bean(initMethod="init", destroyMethod="close")
    DataSource dataSource() {
        System.setProperty("derby.system.home","./logs/derby-data");
        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setUniqueResourceName("JTAResource-"+System.currentTimeMillis());
        dataSource.setDriverClassName(dsDriverClassName);
        dataSource.setUrl(dsUrl);
        dataSource.setUser(dsUsername);
        dataSource.setPassword(dsPassword);
        dataSource.setMaxPoolSize(4);
        dataSource.setTestQuery("VALUES(1)");
        return dataSource;
    }

}
