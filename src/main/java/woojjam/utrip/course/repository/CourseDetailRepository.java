package woojjam.utrip.course.repository;

import woojjam.utrip.course.domain.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {

//    List<CourseDetail> findByUserCourseIdIn(List<Long> id);
//    List<CourseDetail> findByUserCourseId(Long userCourseId);

//    void deleteAllByUserCourseId(Long userCourseId);
}
