package site.grigo.error.exception;

import site.grigo.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String message) {
        super(message, ErrorCode.LOGIN_INPUT_INVALID);
    }
}
