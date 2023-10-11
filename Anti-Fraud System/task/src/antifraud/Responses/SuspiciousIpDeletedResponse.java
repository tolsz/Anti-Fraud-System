package antifraud.Responses;

import lombok.Data;

@Data
public class SuspiciousIpDeletedResponse {
    private String status;

    public SuspiciousIpDeletedResponse(String ip) {
        this.status = "IP " + ip + " successfully removed!";
    }
}

