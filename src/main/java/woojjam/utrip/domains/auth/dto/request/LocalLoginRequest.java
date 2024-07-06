package woojjam.utrip.domains.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalLoginRequest {

	private String email;
	private String password;
}
