package site.grigo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    public LoginDTO(int status) {
        this.status = status;
    }
    public LoginDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
    private int status;
    private String message;
}
