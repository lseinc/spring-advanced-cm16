package com.lse.spring.example.atm;

public interface ATM {
	String BEAN_NAME="diebold";
	String getType();
	double getAccountBalance(String accountNumber);
	double depositToAccount(String accountNumber, double amount);
	double withdrawFromAccount(String accountNumber, double amount);
	void transfer(String fromAccountNumber, String toAccountNumber, double amount);
}
