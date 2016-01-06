package com.lse.spring.example.atm;

import org.springframework.dao.EmptyResultDataAccessException;


public interface AccountDao {
	Account findOne(String accountNumber);
	Account save(Account account);
	void delete(String accountNumber);
	long count();
}
