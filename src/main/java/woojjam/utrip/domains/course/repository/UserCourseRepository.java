package woojjam.utrip.domains.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import woojjam.utrip.domains.course.domain.UserCourse;
import woojjam.utrip.domains.course.dto.UserCourseDetailDto;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {

	List<UserCourse> findByUserId(Long UserId);

	@Query(
		"SELECT new woojjam.utrip.domains.course.dto.UserCourseDetailDto(uc.id, uc.name, uc.createdAt, uc.updatedAt, ucd.dayNum, ucdd.placeOrder, ucddp.name, ucddp.description, ucddp.img,ucddp.px, ucddp.py) "
			+
			"FROM UserCourse uc " +
			"JOIN uc.userCourseDays ucd " +
			"JOIN ucd.courseDetails ucdd " +
			"JOIN ucdd.place ucddp " +
			"WHERE uc.user.id = :userId " +
			"GROUP BY uc.id, uc.name, uc.createdAt, uc.updatedAt, ucd.dayNum, ucdd.placeOrder, ucddp.name, ucddp.description, ucddp.img, ucddp.px, ucddp.py "
			+
			"ORDER BY uc.name, ucd.dayNum, ucdd.placeOrder")
	List<UserCourseDetailDto> findDetailsByUserIdWithFetchJoin(@Param("userId") Long userId);
}
