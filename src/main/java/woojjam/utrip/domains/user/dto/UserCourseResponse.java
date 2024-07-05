package woojjam.utrip.domains.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import woojjam.utrip.domains.course.dto.CourseDto;

@Data
@Builder
public class UserCourseResponse {

	private List<CourseDto> course;

	public static UserCourseResponse from(List<CourseDto> course) {
		return UserCourseResponse.builder()
			.course(course)
			.build();
	}

}
