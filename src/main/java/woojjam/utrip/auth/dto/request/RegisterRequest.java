package woojjam.utrip.auth.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {

	String nickname;
	String email;
	String password;
	String checkPassword;
}
