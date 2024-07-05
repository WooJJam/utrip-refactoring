package woojjam.utrip.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woojjam.utrip.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByRefreshToken(String refreshToken);

	Optional<User> findByEmail(String email);

}
