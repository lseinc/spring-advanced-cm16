package com.lse.spring.example.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
@Profile("account-jdbc")
public class AccountDaoJdbc implements AccountDao {
	private static final String SQL_COUNT_ALL = "SELECT COUNT(1) FROM ACCOUNT";

	private static final String SQL_INSERT_ACCOUNT = "insert into ACCOUNT(acct_number,balance,acct_type) values (:acct,:balance,:type)";

	private static final String SQL_UPDATE_ACCOUNT = "update ACCOUNT set balance=:balance,acct_type=:type where acct_number=:acct";

	private static final String SQL_FETCH_ACCOUNT = "select acct_number, balance, acct_type from account where acct_number=:acct";

	private DataSource dataSource;

	private NamedParameterJdbcTemplate jdbc;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public AccountDaoJdbc() {
	}

	private static class AccountMapper implements RowMapper<Account> {
		public Account mapRow(ResultSet rs, int row) throws SQLException {
			Account obj = new Account();
			obj.setAccount(rs.getString("acct_number"));
			obj.setBalance(rs.getDouble("balance"));
			obj.setType(rs.getString("acct_type"));
			return obj;
		}
	};

	private AccountMapper mapper = new AccountMapper();

	@Transactional(readOnly = true)
	public Account findOne(String accountNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acct", accountNumber);
		Account result = null;
		List<Account> list = jdbc.query(SQL_FETCH_ACCOUNT, map, mapper);
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public long count() {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = jdbc.queryForObject(SQL_COUNT_ALL, map, Integer.class);
		return count;
	}

	@Transactional
	public Account save(Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acct", account.getAccountNumber());
		map.put("type", account.getType());
		map.put("balance", account.getBalance());

		// do we update or insert?
		Account found = findOne(account.getAccountNumber());
		if (found != null) {
			jdbc.update(SQL_UPDATE_ACCOUNT, map);
		} else {
			jdbc.update(SQL_INSERT_ACCOUNT, map);
		}
		// what if the insert or update triggered a data change, get the latest
		// and return it
		found = findOne(account.getAccountNumber());
		return found;
	}

	@Transactional
	public void delete(String accountNumber) {
		Account found = findOne(accountNumber);
		if (found != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("acct", accountNumber);
			jdbc.update("delete from ACCOUNT where acct_number=:acct", map);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{ class: ");
		sb.append(this.getClass().getCanonicalName());
		sb.append(",\n\t size: ").append(count());
		sb.append(" }\n");
		return sb.toString();
	}
}
