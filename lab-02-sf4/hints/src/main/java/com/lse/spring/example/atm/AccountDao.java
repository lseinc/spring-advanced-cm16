package com.lse.spring.example.atm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor = EmptyResultDataAccessException.class)
public interface AccountDao {
	Account findOne(String accountNumber);
	Account save(Account account);
	void delete(String accountNumber);
	long count();
}
