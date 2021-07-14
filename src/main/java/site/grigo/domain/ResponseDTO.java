package site.grigo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDTO {

    // 응답 상태
    private int status;
    // 에러 메세지
    private String errorMessage;

    // 빈 생성자
    public ResponseDTO() {
    }

    // 응답 상태와 메세지 생성자
    public ResponseDTO(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ResponseDTO(int status) {
        this.status = status;
    }
}
