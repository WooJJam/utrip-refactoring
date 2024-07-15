package woojjam.utrip.domains.user.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

	private UserErrorCode userErrorCode;

	public UserException(UserErrorCode userErrorCode) {
		this.userErrorCode = userErrorCode;
	}
}
