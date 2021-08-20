package site.grigo.error.exception;

import site.grigo.error.ErrorCode;

public class TokenNotAllowedException extends BusinessException{

    public TokenNotAllowedException(String message) {
        super(message, ErrorCode.TOKEN_PERMISSION_DENIED);
    }
}
