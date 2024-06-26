package woojjam.utrip.review.repository;

import woojjam.utrip.review.domain.Review;
import woojjam.utrip.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByVideoId(Long videoId);
    Page<Review> findByVideoIdOrderByScoreDesc(Long videoId, Pageable pageable);
    Page<Review> findByVideoIdOrderByCreatedAtDesc(Long videoId, Pageable pageable);

    Optional<Review> findByIdAndUser(Long reviewId, User user);

    void deleteByVideoId(Long videoId);

}
