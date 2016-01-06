package com.lse.spring.example.atm;

import org.joda.time.DateTime;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;


/**
 * Unit test for simple User.
 */
public class UserApplicationTest extends BaseIntegrationTest {

    @BeforeClass
    public static void setupClass() {
        RunDerbyRun.startDatabase();
        RunDerbyRun.setupSchema();
    }

    @AfterClass
    public static void afterClass() {
        RunDerbyRun.shutdownDatabase();
    }

    @Autowired
    private UserApplication user;

    @Autowired
    private AccountDao dao;

    @Value("${my.account.balance}")
    private double initialBalance;

    @Before
    public void setup() {
        Account found = dao.findOne(user.getCheckingAccountNumber());
        if (found != null) {
            Account account = new Account(found);
            account.setBalance(0.0);
            dao.save(account);
        }
    }

    @After
    public void teardown() {
    }

    @Test
    public void testCheckingAccount() {
        double takeOut = 20.0;
        double initialDeposit = 100.0;
        double nextDeposit = 1000000.0;

        double balance = user.depositToChecking(initialDeposit);
        System.out.println("starting balance: " + balance);

        balance = user.depositToChecking(nextDeposit);
        double expected = nextDeposit + initialDeposit;
        Assert.assertEquals("initial balance",expected,balance, 0.0);

        System.out.println("  withdrawing $" + takeOut + " dollars");
        balance = user.withdrawFromChecking(takeOut);
        System.out.println("ending balance: " + balance);

        Assert.assertEquals("new balance", (expected - takeOut), balance, 0.0);

        System.out.println("object tree: " + user);
    }

    @Test
    public void testTransfer() {
        DateTime startTime = new DateTime();  //must be before transactions !!!

        double balance = user.depositToChecking(100.00);
        Assert.assertTrue("above 10", balance > 10.0);

        double checking = user.checkingBalance();
        double savings = user.savingsBalance();

        Assert.assertTrue("above 10", checking > 10.0);
        Assert.assertTrue("above zero", savings > 0.0);

        double transfer = 10.00;
        user.transferFromCheckingToSavings(transfer);

        double checkingAfter = user.checkingBalance();
        double savingsAfter = user.savingsBalance();

        Assert.assertEquals("transfer", checking - transfer, checkingAfter, 0.0);
        Assert.assertEquals("transfer", savings + transfer, savingsAfter, 0.0);

        System.out.println("object tree: " + user);

//		DateTime endTime = startTime.plusSeconds(10);
//
//		List<Audit> auditList = dao.findAudits(user.getCheckingAccountNumber(), startTime, endTime);
//		System.out.println("auditList="+auditList);
//		Assert.assertNotNull(auditList);
//		Assert.assertTrue(auditList.size()>0);
//		Audit item = auditList.get(0);
//		Assert.assertEquals(user.getCheckingAccountNumber(), item.getAccountNumber());
//		Assert.assertTrue("start on or after",item.getEventTime().isAfter(startTime) || item.getEventTime().isEqual(startTime));
//		Assert.assertTrue("end on or before",item.getEventTime().isBefore(endTime) || item.getEventTime().isEqual(endTime));
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}
