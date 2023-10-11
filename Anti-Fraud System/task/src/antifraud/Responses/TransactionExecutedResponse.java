package antifraud.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionExecutedResponse {
    private String result;
    private String info;
}
