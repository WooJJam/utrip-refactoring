package CodeIt.utrip.course.repository;

import CodeIt.utrip.course.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {

    List<UserCourse> findByUserId(Long UserId);

    @Query("SELECT uc FROM UserCourse uc LEFT JOIN FETCH uc.courseDetails WHERE uc.user.id = :userId")
    List<UserCourse> findByUserIdWithDetails(@Param("userId") Long userId);

}
