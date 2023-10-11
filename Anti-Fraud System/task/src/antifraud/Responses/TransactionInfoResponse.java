package antifraud.Responses;

import antifraud.Entities.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionInfoResponse {
    private long transactionId;
    private long amount;
    private String ip;
    private String number;
    private String region;
    private LocalDateTime date;
    private String result;
    private String feedback;

    public TransactionInfoResponse(Transaction transaction) {
        this.transactionId = transaction.getId();
        this.amount = transaction.getAmount();
        this.ip = transaction.getIp();
        this.number = transaction.getNumber();
        this.region = transaction.getRegion();
        this.date = transaction.getDate();
        this.result = transaction.getResult();
        this.feedback = transaction.getFeedback();
    }
}
