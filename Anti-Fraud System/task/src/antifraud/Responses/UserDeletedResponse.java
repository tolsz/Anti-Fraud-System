package antifraud.Responses;

import lombok.Data;

@Data
public class UserDeletedResponse {
    private String username;
    private String status;

    public UserDeletedResponse(String username) {
        this.username = username;
        this.status = "Deleted successfully!";
    }
}
