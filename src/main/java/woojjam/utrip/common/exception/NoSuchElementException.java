package woojjam.utrip.common.exception;

import woojjam.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class NoSuchElementException extends BaseException {
    public NoSuchElementException(StatusCode statusCode) {
        super(statusCode);
    }
}
