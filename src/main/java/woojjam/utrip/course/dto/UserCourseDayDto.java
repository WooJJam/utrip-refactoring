package woojjam.utrip.course.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.course.domain.UserCourseDay;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCourseDayDto {

	private int dayNum;
	private UserCourse userCourse;

	public static UserCourseDay toEntity(int dayNum, UserCourse userCourse) {
		return UserCourseDay.builder()
			.dayNum(dayNum)
			.userCourse(userCourse)
			.build();
	}

}