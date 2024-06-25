package CodeIt.utrip.user.repository;

import CodeIt.utrip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByEmail(String email);

}
