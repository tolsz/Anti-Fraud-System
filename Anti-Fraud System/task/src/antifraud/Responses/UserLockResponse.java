package antifraud.Responses;

import lombok.Data;


@Data
public class UserLockResponse {
    private String status;

    public UserLockResponse(String username, String status) {
        this.status = String.format("User %s %s!", username, status.equals("LOCK") ? "locked" : "unlocked");
    }
}
