import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LoanAppTest {


    private LoanApp loanApp;
    private AdminPortal adminPortal;

    @Before
    public void setUp() throws Exception {
        loanApp = new LoanApp();
         adminPortal = new AdminPortal(loanApp);

        loanApp.createUser("user1");
        loanApp.createUser("user2");

    }

    @Test(expected = Exception.class)
    public void testCreateUserFailure() throws Exception {
        loanApp.createUser("user2");
    }

    @Test
    public void createLoan() throws Exception {
        loanApp.createLoan(10000.0, 3, "user1");
        assertEquals(loanApp.userToLoanMap.get("user1").size(), 1);

        loanApp.createLoan(30000.0, 4, "user1");
        assertEquals(loanApp.userToLoanMap.get("user1").size(), 2);
    }

    @Test
    public void approveLoan() throws Exception {
        loanApp.createLoan(10000.0, 3, "user1");
        assertEquals(loanApp.userToLoanMap.get("user1").size(), 1);

        adminPortal.approveLoan(3);
    }

    @Test (expected = Exception.class)
    public void approveLoanFails() throws Exception {
        loanApp.createLoan(10000.0, 3, "user1");
        assertEquals(loanApp.userToLoanMap.get("user1").size(), 1);

        adminPortal.approveLoan(2);
    }

    @Test
    public void showLoans() throws Exception
    {
        loanApp.createLoan(10000.0, 3, "user1");
        loanApp.createLoan(20000.0, 5, "user1");
        List<Loan> loans = loanApp.getLoansInfo("user1");
        for (Loan loan : loans)
            System.out.println(loan.toString());
    }

    @Test
    public void repaymentOfLoan() throws Exception
    {
        Integer loanId = loanApp.createLoan(10000.0, 3, "user1");
        loanApp.repayLoan(loanId, "user1", 5000.0);
        List<Loan> loans = loanApp.getLoansInfo("user1");
        for (Loan loan : loans)
            System.out.println(loan.toString());

    }

    @Test
    public void repaymentOfLoanPaidCase() throws Exception
    {
        Integer loanId = loanApp.createLoan(10000.0, 2, "user1");
        loanApp.repayLoan(loanId, "user1", 10000.0);
        List<Loan> loans = loanApp.getLoansInfo("user1");
        for (Loan loan : loans)
            System.out.println(loan.toString());
        assertEquals(loans.get(0).getLoanStatus(), LoanStatus.Paid);

    }
}
