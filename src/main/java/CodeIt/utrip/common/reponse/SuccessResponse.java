package CodeIt.utrip.common.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SuccessResponse<T> {

    private String status;
    private String message;
    private T data;

    public SuccessResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static <T> SuccessResponse<T> of(final String status, final String message) {
        return of(status, message, null);
    }

    public static <T> SuccessResponse<T> of (String status, String message, T data) {
        return SuccessResponse.<T>builder()
                .data(data)
                .status(status)
                .message(message)
                .build();
    }
}
