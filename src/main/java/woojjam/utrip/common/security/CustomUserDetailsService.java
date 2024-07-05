package woojjam.utrip.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.security.authentication.SecurityUserDetails;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("CustomUserDetailsService Start");
		log.info("email : {}", email);

		User user = userService.findUserByEmail(email);
		return SecurityUserDetails.from(user);
	}
}
