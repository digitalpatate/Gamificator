package ch.heigvd.amt.gamificator.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException (String msg) {
        super(HttpStatus.NOT_FOUND, msg);
    }
}
