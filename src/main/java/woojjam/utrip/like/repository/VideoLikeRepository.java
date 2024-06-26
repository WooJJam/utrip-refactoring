package woojjam.utrip.like.repository;

import woojjam.utrip.like.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    boolean existsByVideoIdAndUserId(Long videoId, Long userId);
    List<VideoLike> findByUserId(Long userId);

    void deleteByUserIdAndVideoId(Long userId, Long videoId);
    void deleteByVideoId(Long videoId);

}
