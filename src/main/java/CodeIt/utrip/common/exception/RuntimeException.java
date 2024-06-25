package CodeIt.utrip.common.exception;

import CodeIt.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class RuntimeException extends BaseException {
    public RuntimeException(StatusCode statusCode) {
        super(statusCode);
    }
}
