package antifraud.Requests;

import lombok.Data;

@Data
public class TransactionRequest {
    private String IP;
    private String number;
    private long amount;
    private String region;
    private String date;
}
