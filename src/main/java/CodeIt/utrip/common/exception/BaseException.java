package CodeIt.utrip.common.exception;

import CodeIt.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class BaseException extends java.lang.RuntimeException {

    private final String status;

    public BaseException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.status = statusCode.getCode();
    }
}
