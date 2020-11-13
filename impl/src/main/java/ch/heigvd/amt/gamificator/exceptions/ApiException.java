package ch.heigvd.amt.gamificator.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception {
    private HttpStatus code;
    public ApiException (HttpStatus code, String msg) {
        super(msg);
        this.code = code;
    }
}
