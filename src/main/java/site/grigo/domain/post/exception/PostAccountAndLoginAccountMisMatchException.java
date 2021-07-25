package site.grigo.domain.post.exception;

import site.grigo.error.exception.ChangePermissionException;

public class PostAccountAndLoginAccountMisMatchException extends ChangePermissionException {
    public PostAccountAndLoginAccountMisMatchException() {
        super("Login account and post account do not match.");
    }
}
