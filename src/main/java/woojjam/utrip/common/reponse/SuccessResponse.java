package woojjam.utrip.common.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ResponseEntity<SuccessResponse<T>> of(final String status, final String message) {
        return of(status, message, null);
    }

    public static <T> ResponseEntity<SuccessResponse<T>> of (String status, String message, T data) {
        return ResponseEntity.ok(SuccessResponse.<T>builder()
                .data(data)
                .status(status)
                .message(message)
                .build());
    }
}
