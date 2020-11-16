package ch.heigvd.amt.gamificator.exceptions;

public class RelatedObjectNotFound extends BadRequestException{
    public RelatedObjectNotFound(String msg) {
        super(msg);
    }
}
