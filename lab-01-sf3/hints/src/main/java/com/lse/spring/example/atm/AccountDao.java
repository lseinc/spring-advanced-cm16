package com.lse.spring.example.atm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface AccountDao {
	Account fetchAccount(String accountNumber);
	Account save(Account account);
	Account remove(String accountNumber);
	int countAllAccounts();
}
