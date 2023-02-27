import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanApp {

    Map<String, List<Integer>> userToLoanMap;
    Map<Integer, Loan> loanIdToLoanMap;
    Map<String, User> userIdToUserMap;

    public LoanApp()
    {
        userIdToUserMap = new HashMap<>();
        loanIdToLoanMap = new HashMap<>();
        userToLoanMap = new HashMap<>();
    }

    public void createUser(String userName) throws Exception {
        if(userIdToUserMap.containsKey(userName))
        {
            throw new Exception("Username already present");
        }
        userIdToUserMap.put(userName, new User(userName));
    }

    public Integer createLoan(Double amount, Integer repaymentTerm, String userName) throws Exception {
        if(!userIdToUserMap.containsKey(userName))
        {
            throw new Exception("user not present");
        }
        Loan loan = new Loan(amount, repaymentTerm);
        loanIdToLoanMap.put(loan.getLoanId(), loan);
        List<Integer> loans  =userToLoanMap.containsKey(userName) ? userToLoanMap.get(userName) : new ArrayList<Integer>();
        loans.add(loan.getLoanId());
        userToLoanMap.put(userName, loans);
        return loan.getLoanId();
    }

    public List<Loan> getLoansInfo(String userName) throws Exception {
        if(!userIdToUserMap.containsKey(userName))
        {
            throw new Exception("user not present");
        }
        List<Integer> loans = userToLoanMap.containsKey(userName) ? userToLoanMap.get(userName) : new ArrayList<Integer>();
        List<Loan> loanList = new ArrayList<>() ;
        for(Integer loan : loans)
        {
            loanList.add(loanIdToLoanMap.get(loan));
        }
        return loanList;
    }

    public List<Loan> showAllLoans(){
        List<Loan> loanList = new ArrayList<>() ;
        for (Map.Entry<Integer, Loan> entry : loanIdToLoanMap.entrySet()){
            loanList.add(entry.getValue());
        }
        return loanList;
    }

    public void approveLoan(Integer loanId) throws Exception {

     Loan loan =loanIdToLoanMap.containsKey(loanId) ? loanIdToLoanMap.get(loanId) : null;

     if(loan == null){
         throw new Exception("Loan id not present");
     }
     loan.setLoanStatus(LoanStatus.Approved);

     return ;
    }

    public void repayLoan(Integer loanId, String userName, Double amount) throws Exception {

        Loan loan =loanIdToLoanMap.containsKey(loanId) ? loanIdToLoanMap.get(loanId) : null;
        if(loan == null)
        {
            throw new Exception("Loan id not present");
        }
        List<Integer> loans = userToLoanMap.containsKey(userName) ? userToLoanMap.get(userName) : null;
        if(loans == null)
        {
            throw new Exception("User id not present");
        }
        if(!loans.contains(loanId))
        {
            throw new Exception("loan details not matching for current user");
        }

        List<Payments> payments = loan.getPayments();
        for(Payments pay : payments)
        {
            if(pay.getPaymentStatus() == PaymentStatus.Pending && amount >= pay.getAmount())
            {
                pay.setPaymentStatus(PaymentStatus.Paid);
                amount -= pay.getAmount();
            }
            else if(pay.getPaymentStatus() == PaymentStatus.Pending && amount < pay.getAmount())
            {
                pay.setPaymentStatus(PaymentStatus.Pending);
                pay.setAmount(pay.getAmount()-amount);
                amount = 0.0;
                break;
            }
        }
        loan.checKLoanIsPaid();
    }


}
