package CodeIt.utrip.common.exception;

import CodeIt.utrip.common.reponse.StatusCode;
import lombok.Getter;

@Getter
public class UserException extends BaseException{

    public UserException(StatusCode statusCode) {
        super(statusCode);
    }
}