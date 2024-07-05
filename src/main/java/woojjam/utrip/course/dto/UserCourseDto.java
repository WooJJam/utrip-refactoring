package woojjam.utrip.course.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserCourseDto {

	private User user;
	private String name;

	public static UserCourse toEntity(User user, String name) {
		return UserCourse.builder()
			.user(user)
			.name(name)
			.build();
	}
}
