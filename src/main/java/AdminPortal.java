public class AdminPortal {


    LoanApp loanApp;

    public AdminPortal(LoanApp loanApp)
    {
        this.loanApp = loanApp;
    }

    public void approveLoan(Integer loanId) throws Exception {
        loanApp.approveLoan(loanId);
    }
}
