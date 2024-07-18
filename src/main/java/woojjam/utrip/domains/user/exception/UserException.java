package woojjam.utrip.domains.user.exception;

import lombok.Getter;
import woojjam.utrip.common.exception.GlobalException;

@Getter
public class UserException extends GlobalException {

	private final UserErrorCode userErrorCode;

	public UserException(UserErrorCode userErrorCode) {
		super(userErrorCode);
		this.userErrorCode = userErrorCode;
	}
}
