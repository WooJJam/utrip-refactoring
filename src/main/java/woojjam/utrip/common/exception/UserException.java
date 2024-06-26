package woojjam.utrip.common.exception;

import woojjam.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class UserException extends BaseException{

    public UserException(StatusCode statusCode) {
        super(statusCode);
    }
}