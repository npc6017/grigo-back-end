package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinDTO {

    // 빈 생성자
    public JoinDTO() {
    }

    // 응답 상태와 메세지 생성자
    public JoinDTO(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    // 응답 상태
    private int status;
    // 에러 메세지
    private String errorMessage;
}
