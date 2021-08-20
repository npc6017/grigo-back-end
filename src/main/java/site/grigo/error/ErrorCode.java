package site.grigo.error;

public enum ErrorCode {
    LOGIN_INPUT_INVALID(400, "B002", "Login input is invalid"),
    PERMISSION_DENIED(401, "B003", "Change permission denied"),
    TOKEN_PERMISSION_DENIED(400, "B004", "Token is denied");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
