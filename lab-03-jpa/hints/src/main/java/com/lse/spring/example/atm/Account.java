package com.lse.spring.example.atm;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="ACCOUNT")
public class Account  {

	@NotNull
	@Size(max=10,min=10)
	@Id
	@Column(name = "ACCT_NUMBER")
	private String accountNumber;

	@NotNull
	@Column(name = "ACCT_TYPE")
	private String type;


	@Min(25)
	@Column(name="BALANCE")
	private Double balance;

	public Account() {
	}

	public Account(String type, String accountNumber, double balance) {
		this.type = type;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public Account(Account account) {
		this.type = account.getType();
		this.accountNumber = account.getAccountNumber();
		this.balance = account.getBalance();
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccount(String account) {
		this.accountNumber = account;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double credit(double amount) {
		balance += amount;
		return balance;
	}

	public double debit(double amount) {
		balance -= amount;
		return balance;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ class: ");
		sb.append(this.getClass().getCanonicalName());
		sb.append(",\n\t type: ").append(type);
		sb.append(",\n\t accountNumber: ").append(accountNumber);
		sb.append(",\n\t balance: ").append(balance);
		sb.append(" }\n");
		return sb.toString();
	}
}
