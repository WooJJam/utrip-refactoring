package woojjam.utrip.domains.like.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woojjam.utrip.domains.like.domain.VideoLike;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
	boolean existsByVideoIdAndUserId(Long videoId, Long userId);

	List<VideoLike> findByUserId(Long userId);

	void deleteByUserIdAndVideoId(Long userId, Long videoId);

	void deleteByVideoId(Long videoId);

}
