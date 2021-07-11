package site.grigo.error;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;

    public ErrorResponse(List<FieldError> errors, String code) {
        this.errors = errors;
        this.code = code;
    }

}
