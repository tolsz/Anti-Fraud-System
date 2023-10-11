package antifraud.Requests;

import lombok.Data;

@Data
public class ChangeRoleRequest {
    private String username;
    private String role;
}

