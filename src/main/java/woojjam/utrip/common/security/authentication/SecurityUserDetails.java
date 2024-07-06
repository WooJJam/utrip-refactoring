package woojjam.utrip.common.security.authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.domains.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUserDetails implements UserDetails {

	private Long userId;
	private String email;
	private Collection<GrantedAuthority> authorities;

	@Builder
	public SecurityUserDetails(Long userId, String email, Collection<GrantedAuthority> authorities) {
		this.userId = userId;
		this.email = email;
		this.authorities = authorities;
	}

	public static SecurityUserDetails from(User user) {
		return SecurityUserDetails.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
