package com.lse.spring.example.atm;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("account-jpa")
public interface AccountDaoJPA extends AccountDao, JpaRepository<Account,String> {

}
