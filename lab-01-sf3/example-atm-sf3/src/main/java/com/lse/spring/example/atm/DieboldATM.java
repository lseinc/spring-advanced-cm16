package com.lse.spring.example.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ddlucas
 */

@Service
public class DieboldATM implements ATM {
	@Value("${my.atm.type}") 
	private String type = "DieboldATM";
	
	@Autowired
	private Bank bank;
	
	public DieboldATM() {
		System.out.println("creating DieboldATM...");
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAccountBalance(String accountNumber) {
		return bank.getAccountBalance(accountNumber);
	}

	public double depositToAccount(String accountNumber, double amount) {
		return bank.creditAccount(accountNumber,amount);
	}

	public double withdrawFromAccount(String accountNumber, double amount) {
		return bank.debitAccount(accountNumber, amount);
	}

	public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
		bank.transfer(fromAccountNumber, toAccountNumber, amount);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ class: ");
		sb.append(this.getClass().getCanonicalName());
		sb.append(",\n\t type: ").append(type);
		sb.append(",\n\t bank: ").append(bank);
		sb.append(" }\n");
		return sb.toString();
	}

}
