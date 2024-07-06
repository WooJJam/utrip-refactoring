package woojjam.utrip.domains.auth.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {

	private String email;
	private String newPassword;
	private String checkPassword;
}
