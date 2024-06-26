package woojjam.utrip.common.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse<T> {
    private String status;
    private String message;

    public static <T> ErrorResponse<T> of (final String status, final String message) {
        return ErrorResponse.<T>builder()
                .message(message)
                .status(status)
                .build();
    }
}
