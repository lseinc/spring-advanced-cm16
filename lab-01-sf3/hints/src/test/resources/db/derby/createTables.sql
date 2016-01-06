-- create Account table
create table ACCOUNT( acct_number VARCHAR(10) PRIMARY KEY,
		      balance DECIMAL(12,2) DEFAULT 0,
		      acct_type VARCHAR(32) NOT NULL
);

