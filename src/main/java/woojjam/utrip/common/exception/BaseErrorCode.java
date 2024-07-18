package woojjam.utrip.common.exception;

public interface BaseErrorCode {

	ErrorCausedBy causedBy();

	String getErrorMessage();
}
