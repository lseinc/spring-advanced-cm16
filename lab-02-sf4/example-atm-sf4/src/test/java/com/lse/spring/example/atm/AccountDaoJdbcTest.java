package com.lse.spring.example.atm;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountDaoJdbcTest extends BaseIntegrationTest {

    private static final String ACCT1 = "9999887777";
    private static final String ACCT2 = "9999887778";
    private static final String ACCT3 = "9999887779";

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
    private AccountDao dao;

    Account acct1 = new Account("Checking", ACCT1, 12.34);
    Account acct2 = new Account("Savings", ACCT2, 1234.56);

    @Before
    public void setUp() throws Exception {
        dao.delete(ACCT1);
        dao.delete(ACCT2);
        dao.delete(ACCT3);
        dao.save(acct1);
        dao.save(acct2);
    }

    @After
    public void tearDown() throws Exception {
        dao.delete(ACCT1);
        dao.delete(ACCT2);
        dao.delete(ACCT3);
    }

    @Test
    public void testFetchAccount() {
        Account found = dao.findOne(ACCT1);
        Assert.assertEquals("found", ACCT1, found.getAccountNumber());
    }

    @Test
    public void testSaveInsert() {
        Account found = dao.findOne(ACCT3);
        Assert.assertNull("not found", found);

        Account account = new Account("Savings", ACCT3, 4321.00);
        Account result = dao.save(account);
        Assert.assertNotNull("saved", result);

        found = dao.findOne(ACCT3);
        Assert.assertNotNull("found", found);
        Assert.assertEquals("found", ACCT3, found.getAccountNumber());
    }

    @Test
    public void testSaveUpdate() {
        Account found = dao.findOne(ACCT1);
        Assert.assertNotNull("found", found);
        Account expected = new Account(found);
        expected.setBalance(-100.0);
        dao.save(expected);

        Account actual = dao.findOne(ACCT1);
        Assert.assertNotNull("saved", actual);
        Assert.assertEquals("changed balance",
                Double.valueOf(expected.getBalance()),
                Double.valueOf(actual.getBalance()));

        found = dao.findOne(ACCT1);
        Assert.assertNotNull("found", found);
        Assert.assertEquals("found", ACCT1, found.getAccountNumber());
    }

    @Test
    public void testRemove() {
        dao.delete(ACCT2);
        Account removed = dao.findOne(ACCT2);
        Assert.assertNull("removed", removed);

    }

    @Test
    public void testCountAllAccounts() {
        long actual = dao.count();
        Assert.assertTrue("count", actual >= 3);
    }

}
