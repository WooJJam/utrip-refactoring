package woojjam.utrip.domains.video.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import woojjam.utrip.domains.video.domain.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	@Query("select v from Video v order by v.likeCount DESC")
	List<Video> findTopByOrderByLikeCountDesc(Pageable pageable);

	Page<Video> findByTagsContaining(String tag, Pageable pageable);

	Page<Video> findAllByOrderByLikeCountDesc(Pageable pageable);

	Page<Video> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("select v from Video v")
	Page<Video> findAll(Pageable pageable);

	List<Video> findByIdIn(List<Long> videoId);

}
