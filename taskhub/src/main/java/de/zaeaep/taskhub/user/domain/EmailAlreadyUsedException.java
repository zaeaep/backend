package de.zaeaep.taskhub.user.domain;

import de.zaeaep.taskhub.common.error.ApiException;

public class EmailAlreadyUsedException extends ApiException{
    public EmailAlreadyUsedException(String email) {
        super ("EMAIL_ALREADY_USED", "Email: '" + email + "'' is already Used");
    }
}
