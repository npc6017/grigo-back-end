package site.grigo.domain.account.exception;

import site.grigo.error.exception.EntityNotFoundException;

public class AccountInformationIncorrectException extends EntityNotFoundException {
    public AccountInformationIncorrectException(String target) {
        super(target);
    }
}
