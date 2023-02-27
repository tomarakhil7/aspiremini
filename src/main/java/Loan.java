import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Loan {

    private static Integer id = 1;
    private Integer loanId ;
    private Double amount;
    private Integer repaymentTerm;
    private List<Payments> payments;
    private LoanStatus loanStatus;

    public Loan(Double amount, Integer repaymentTerm)
    {
        id++;
        loanId = id;
        this.amount= amount;
        this.repaymentTerm =repaymentTerm;
        payments = new ArrayList<Payments>();
        for(int i =0 ;i< repaymentTerm; i++)
        {
            payments.add(new Payments(amount/(repaymentTerm*1.0), PaymentStatus.valueOf("Pending")));
        }
        loanStatus = LoanStatus.waiting_Approval;
    }

    public boolean checKLoanIsPaid()
    {
        for(Payments pay : payments){

            if(pay.getPaymentStatus() ==  PaymentStatus.Pending)
                return false;
        }
        loanStatus = LoanStatus.Paid;
        return  true;

    }

    @Override
    public String toString() {
        return "id: " + this.loanId.toString() + " status " + this.loanStatus + " loan amount: " + this.amount.toString() +" term: " +this.repaymentTerm.toString() + payments.toString();
    }

}
