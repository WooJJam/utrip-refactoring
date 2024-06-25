package CodeIt.utrip.common.exception;

import CodeIt.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class NoSuchElementException extends BaseException {
    public NoSuchElementException(StatusCode statusCode) {
        super(statusCode);
    }
}
