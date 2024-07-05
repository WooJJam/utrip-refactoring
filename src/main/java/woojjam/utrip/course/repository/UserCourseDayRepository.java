package woojjam.utrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woojjam.utrip.course.domain.UserCourseDay;

@Repository
public interface UserCourseDayRepository extends JpaRepository<UserCourseDay, Long> {
}
