package woojjam.utrip.course.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.common.domain.BaseEntity;
import woojjam.utrip.course.dto.CourseListDto;
import woojjam.utrip.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCourse extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_course_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "userCourse")
	private List<UserCourseDay> userCourseDays = new ArrayList<>();

	private String name;

	public void updateUserCourse(User user, CourseListDto userCourse) {
		this.user = user;
		this.name = userCourse.getName();
	}
}
