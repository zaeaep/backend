package de.zaeaep.taskhub.user.domain;

import de.zaeaep.taskhub.common.error.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(long id) {
        super("USER_NOT_FOUND", "User with id " + id + " not found");
    }
}
