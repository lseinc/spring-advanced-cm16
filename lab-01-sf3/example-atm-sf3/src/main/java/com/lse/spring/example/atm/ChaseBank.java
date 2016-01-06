package com.lse.spring.example.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author   ddlucas
 */
@Service
public class ChaseBank implements Bank {
	@Value("${my.bank.name}")
	private String bankName = "JPMChase";	

	@Autowired
	private AccountDao accountDao;
	
	public ChaseBank() {
		super();
		System.out.println("creating ChaseBank...");		
	}
	
	@Transactional
	public double creditAccount(String accountNumber, double amount) {
		Account account = fetchAccount(accountNumber);
		System.out.println("bank checking balance: "+account.getBalance()+" for accountNumber="+accountNumber);
		System.out.println("adding amount="+amount);
		account.credit(amount);
		account = accountDao.save(account);
		System.out.println("bank checking balance: "+account.getBalance()+" for accountNumber="+accountNumber);
	
//		accountDao.audit("credit attempted", account.getAccountNumber(), amount);
		
		return account.getBalance();
	}

	@Transactional
	public double debitAccount(String accountNumber, double amount) {
		Account account = fetchAccount(accountNumber);
		System.out.println("bank checking balance: "+account.getBalance()+" for accountNumber="+accountNumber);
		System.out.println("subtracting amount="+amount);
		account.debit(amount);
		account = accountDao.save(account);
		System.out.println("bank checking balance: "+account.getBalance()+" for accountNumber="+accountNumber);
		
		return account.getBalance();
	}

	public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
		System.out.println("transfer "+amount+" from account="+fromAccountNumber+" to account="+toAccountNumber);

		Account toAccount = fetchAccount(toAccountNumber);
		Account fromAccount = fetchAccount(fromAccountNumber);		
		
		System.out.println("transfer to   account="+toAccountNumber+", balance="+toAccount.getBalance());
		System.out.println("transfer from account="+fromAccountNumber+", balance="+fromAccount.getBalance());

		// put money in 
		creditAccount(toAccountNumber, amount);

		//TODO: potential concern
//		if (true) {
//			throw new RuntimeException("OOPS ERROR");
//		}
//		try {
//			Thread.sleep(300000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// get money out 
		debitAccount(fromAccountNumber, amount);

		toAccount = fetchAccount(toAccountNumber);
		fromAccount = fetchAccount(fromAccountNumber);		

		System.out.println("new to   balance: account="+toAccountNumber+", balance="+toAccount.getBalance());
		System.out.println("new from balance: account="+fromAccountNumber+", balance="+fromAccount.getBalance());
	}

	
	private Account fetchAccount(String accountNumber) {
		System.out.println("looking up accountNumber="+accountNumber);
		Account account = accountDao.fetchAccount(accountNumber);
		if (account==null) {
			throw new MissingAccountException("account "+account+" not found");
		}
		return account;
	}
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Transactional(readOnly=true)
	public double getAccountBalance(String accountNumber) {
		Account account = fetchAccount(accountNumber);
		return account.getBalance();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ class: ");
		sb.append(this.getClass().getCanonicalName());
		sb.append(",\n\t bankName: ").append(bankName);
		sb.append(",\n\t accountDao: ").append(accountDao);
		sb.append(" }\n");		
		return sb.toString();
	}
	
}
