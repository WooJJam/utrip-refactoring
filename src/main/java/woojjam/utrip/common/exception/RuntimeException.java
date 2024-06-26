package woojjam.utrip.common.exception;

import woojjam.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class RuntimeException extends BaseException {
    public RuntimeException(StatusCode statusCode) {
        super(statusCode);
    }
}
