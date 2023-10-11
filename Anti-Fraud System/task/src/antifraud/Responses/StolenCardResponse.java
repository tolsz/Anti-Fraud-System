package antifraud.Responses;

import antifraud.Entities.StolenCard;
import lombok.Data;

@Data
public class StolenCardResponse {
    private long id;
    private String number;

    public StolenCardResponse(StolenCard stolenCard) {
        this.id = stolenCard.getId();
        this.number = stolenCard.getNumber();
    }
}
