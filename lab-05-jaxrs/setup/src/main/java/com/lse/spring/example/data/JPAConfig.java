package com.lse.spring.example.data;

import com.atomikos.icatch.jta.*;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.lse.spring.example.atm.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lse.spring.example")
public class JPAConfig {

    @Autowired
    Environment environment;

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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.DERBY);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource());

        return factory;
    }

    @Bean
    UserTransactionManager userTransactionManager() {
        UserTransactionManager utm = new UserTransactionManager();
        try {
            utm.setForceShutdown(true);
            utm.setTransactionTimeout(900);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return utm;
    }

    @Bean
    UserTransactionImp userTransactionImp() {
        UserTransactionImp ut = new UserTransactionImp();
        try {
            ut.setTransactionTimeout(900);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return ut;
    }

    @Bean
    JtaTransactionManager jtaTransactionManager() {
        JtaTransactionManager jtaTM = new JtaTransactionManager();
        jtaTM.setTransactionManager(userTransactionManager());
        jtaTM.setUserTransaction(userTransactionImp());
        return jtaTM;
    }

    @Bean
    DieboldATM dieboldATM() {
        return new DieboldATM();
    }

    @Bean
    ChaseBank chaseBank() {
        return new ChaseBank();
    }

}
