package site.grigo.domain.post.exception;

import site.grigo.error.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException(String target) {
        super(target + " post not found");
    }
}
