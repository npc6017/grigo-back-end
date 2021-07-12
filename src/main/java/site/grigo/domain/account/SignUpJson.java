package site.grigo.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpJson {

    /** 테스트용 생성자*/
    public SignUpJson(String email, String password, String name, String birth, Integer student_id, String sex, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.student_id = student_id;
        this.sex = sex;
        this.phone = phone;
    }

    @Email
    @NotBlank
    private String email;

    @Length(min = 8, max = 50)
    private String password;

    private String name;

    private String birth;

    private Integer student_id;

    private String sex;

    private String phone;
}
