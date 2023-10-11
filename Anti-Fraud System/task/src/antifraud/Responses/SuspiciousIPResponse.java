package antifraud.Responses;

import antifraud.Entities.SuspiciousIP;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuspiciousIPResponse {
    private long id;
    private String ip;

    public SuspiciousIPResponse(SuspiciousIP suspiciousIP) {
        this.id = suspiciousIP.getId();
        this.ip = suspiciousIP.getIp();
    }
}
