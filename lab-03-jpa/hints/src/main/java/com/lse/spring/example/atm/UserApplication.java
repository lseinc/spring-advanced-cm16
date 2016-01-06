package com.lse.spring.example.atm;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author   ddlucas
 */
@Component
public class UserApplication {
	@Autowired
	private ATM atm;
	
	@Value("${my.account.checking.number}")
	private String checkingAccountNumber;
	
	@Value("${my.account.savings.number}")
	private String savingsAccountNumber;

	public ATM getAtm() {
		return atm;
	}

	public void setAtm(ATM atm) {
		this.atm = atm;
	}

	public String getCheckingAccountNumber() {
		return checkingAccountNumber;
	}

	public void setCheckingAccountNumber(String checkingAccountNumber) {
		this.checkingAccountNumber = checkingAccountNumber;
	}
	public String getSavingsAccountNumber() {
		return savingsAccountNumber;
	}

	public void setSavingsAccountNumber(String savingsAccountNumber) {
		this.savingsAccountNumber = savingsAccountNumber;
	}


	public UserApplication() {
		System.out.println("creating User...");
	}
	
	public double withdrawFromChecking(double amount) {
		return atm.withdrawFromAccount(checkingAccountNumber,amount);
	}
	
	public double depositToChecking(double amount) {
		return atm.depositToAccount(checkingAccountNumber,amount);
	}

	public double withdrawFromSavings(double amount) {
		return atm.withdrawFromAccount(savingsAccountNumber,amount);
	}
	
	public double depositToSavings(double amount) {
		return atm.depositToAccount(savingsAccountNumber,amount);
	}

	public double savingsBalance() {
		return atm.getAccountBalance(savingsAccountNumber);
	}
	
	public double checkingBalance() {
		return atm.getAccountBalance(checkingAccountNumber);
	}

	public void transferFromCheckingToSavings(double amount) {
		atm.transfer(checkingAccountNumber, savingsAccountNumber, amount);
	}

	public void transferFromSavingsToChecking(double amount) {
		atm.transfer(savingsAccountNumber, checkingAccountNumber, amount);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ class: ");
		sb.append(this.getClass().getCanonicalName());
		sb.append(",\n\t atm: ").append(atm);
		sb.append(",\n\t checkingAccountNumber: ").append(checkingAccountNumber);
		sb.append(" }\n");		
		return sb.toString();
	}
}
