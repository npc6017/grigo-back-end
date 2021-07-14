package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class PasswordUpdateDTO {

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
