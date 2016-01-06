package com.lse.spring.example.atm;

public interface Bank {
	String getBankName();
	double getAccountBalance(String accountNumber);
	double creditAccount(String accountNumber, double amount);
	double debitAccount(String accountNumber, double amount);
	void transfer(String fromAccountNumber, String toAccountNumber, double amount);
}
