package com.lse.spring.example.atm;

import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;


@Transactional(dontRollbackOn = {EmptyResultDataAccessException.class})
public interface AccountDao {
	Account findOne(String accountNumber);
	Account save(Account account);
	void delete(String accountNumber);
	long count();
}
