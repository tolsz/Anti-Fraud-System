package antifraud.Responses;

import lombok.Data;

@Data
public class StolenCardDeletedResponse {
    private String status;

    public StolenCardDeletedResponse(String number) {
        this.status = "Card " + number + " successfully removed!";
    }
}
