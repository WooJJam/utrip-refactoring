package woojjam.utrip.domains.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woojjam.utrip.domains.course.domain.CourseDetail;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {

	//    Optional<CourseDetail> findByUserCourseDay(Long userCourseDayId);
	//    List<CourseDetail> findByUserCourseIdIn(List<Long> id);
	//    List<CourseDetail> findByUserCourseId(Long userCourseId);

	//    void deleteAllByUserCourseId(Long userCourseId);

}
