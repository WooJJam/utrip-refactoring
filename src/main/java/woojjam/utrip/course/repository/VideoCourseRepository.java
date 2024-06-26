package woojjam.utrip.course.repository;

import woojjam.utrip.course.domain.VideoCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoCourseRepository extends JpaRepository<VideoCourse, Long> {

    Optional<VideoCourse> findByVideoId(Long videoId);
    void deleteByVideoId(Long videoId);
}
