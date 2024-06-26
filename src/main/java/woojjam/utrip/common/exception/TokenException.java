package woojjam.utrip.common.exception;

import woojjam.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class TokenException extends BaseException {

    public TokenException(StatusCode statusCode) {
        super(statusCode);
    }
}
