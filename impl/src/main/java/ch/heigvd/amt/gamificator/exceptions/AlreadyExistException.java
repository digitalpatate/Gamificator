package ch.heigvd.amt.gamificator.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends ApiException {
    public AlreadyExistException(String msg) {
        super(HttpStatus.FOUND, msg);
    }
}
