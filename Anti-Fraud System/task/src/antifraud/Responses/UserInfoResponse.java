package antifraud.Responses;

import antifraud.Entities.User;
import lombok.Data;

@Data
public class UserInfoResponse {
    private long id;
    private String name;
    private String username;
    private String role;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
