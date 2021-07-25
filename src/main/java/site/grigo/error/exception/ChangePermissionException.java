package site.grigo.error.exception;

import site.grigo.error.ErrorCode;

public class ChangePermissionException extends BusinessException{
    public ChangePermissionException(String message) {
        super(message, ErrorCode.PERMISSION_DENIED);
    }
}
