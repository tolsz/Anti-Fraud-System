package antifraud.Requests;

import lombok.Data;

@Data
public class UserLockRequest {
    private String username;
    private String operation;
}
