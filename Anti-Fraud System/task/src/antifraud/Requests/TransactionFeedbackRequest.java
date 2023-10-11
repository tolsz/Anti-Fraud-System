package antifraud.Requests;

import lombok.Data;

@Data
public class TransactionFeedbackRequest {
    private long transactionId;
    private String feedback;
}
