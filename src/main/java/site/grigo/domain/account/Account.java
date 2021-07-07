package site.grigo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Long ID;

    public Account(String email, String password, String name, String birth, Integer student_id, String sex, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.student_id = student_id;
        this.sex = sex;
        this.phone = phone;
    }

    private String email;
    private String password;
    private String name;
    private String birth;
    private Integer student_id;
    private String sex;
    private String phone;
}
