package ch.heigvd.amt.gamificator.exceptions;

import org.springframework.http.HttpStatus;

import java.net.http.HttpRequest;

public class BadRequestException extends ApiException{
    public BadRequestException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
