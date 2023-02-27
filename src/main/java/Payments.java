import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Payments  {
    private Double amount;
    private PaymentStatus paymentStatus;

    @Override
    public String toString() {
        return "amount for repayment: " + this.amount + " payment status: " + paymentStatus ;
    }
}

