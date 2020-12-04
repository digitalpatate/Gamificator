package ch.heigvd.amt.gamificator.exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends ApiException {
    public NotAuthorizedException(String msg) {
        super(HttpStatus.NOT_FOUND, msg);
    }
}
