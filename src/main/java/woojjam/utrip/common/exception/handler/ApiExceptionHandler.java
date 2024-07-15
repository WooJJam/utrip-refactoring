package woojjam.utrip.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.ErrorResponse;
import woojjam.utrip.domains.user.exception.UserException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<?> UserExceptionHandler(UserException e) {

		log.warn("User Exception = {}", e.getUserErrorCode().getMessage());
		// if (e.getStatus().equals(StatusCode.BAD_REQUEST)) {
		// 	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getStatus(), e.getMessage()));
		// }

		// if (e.getStatus().equals(StatusCode.DUPLICATE_EMAIL.getCode())) {
		// 	return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of(e.getStatus(), e.getMessage()));
		// }

		// if (e.getStatus().equals(StatusCode.USER_COURSE_NOT_FOUND.getCode())) {
		// 	return ResponseEntity.ok(ErrorResponse.of(StatusCode.USER_COURSE_NOT_FOUND.getCode(),
		// 		StatusCode.USER_COURSE_NOT_FOUND.getMessage()));
		// }

		return ResponseEntity.status(e.getUserErrorCode().getStatusCode().getCode())
			.body(ErrorResponse.of(e.errorCausedBy().getCode(), e.explainErrorMessage()));
		// .body(ErrorResponse.of(UserErrorCode.causedBy(StatusCode.NOT_FOUND, ReasonCode.INVALID_REQUEST_SYNTAX),
		// 	e.getMessage()));
	}

	// @ExceptionHandler(TokenException.class)
	// public ResponseEntity<?> TokenExceptionHandler(TokenException e) {
	// 	log.error("Token Exception = {}, {}", e.getMessage(), e.getStatus());
	// 	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getStatus(), e.getMessage()));
	// }
	//
	// @ExceptionHandler(RuntimeException.class)
	// public ResponseEntity<?> RunTimeExceptionHandler(RuntimeException e) {
	// 	log.error("RunTimeException = {}, {}", e.getMessage(), e.getStatus());
	// 	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// 		.body(ErrorResponse.of(e.getStatus(), e.getMessage()));
	// }
	//
	// @ExceptionHandler(NoSuchElementException.class)
	// public ResponseEntity<?> NoSuchExceptionHandler(NoSuchElementException e) {
	// 	log.error("NoSuchException = {}, {}", e.getMessage(), e.getStatus());
	//
	// 	// if (e.getStatus().equals(StatusCode.REVIEW_NOT_FOUND.getCode())) {
	// 	// 	return ResponseEntity.ok().body(ErrorResponse.of(e.getStatus(), e.getMessage()));
	// 	// }
	//
	// 	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getStatus(), e.getMessage()));
	// }
}
