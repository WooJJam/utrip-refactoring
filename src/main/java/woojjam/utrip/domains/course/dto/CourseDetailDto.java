package woojjam.utrip.domains.course.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.domains.course.domain.CourseDetail;
import woojjam.utrip.domains.course.domain.UserCourse;
import woojjam.utrip.domains.course.domain.UserCourseDay;
import woojjam.utrip.domains.place.domain.Place;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CourseDetailDto {

	private Place place;
	private UserCourse userCourse;
	private int placeOrder;
	private int dayNum;

	public static CourseDetail toEntity(Place place, UserCourseDay userCourseDay, int order) {
		return CourseDetail.builder()
			.place(place)
			.userCourseDay(userCourseDay)
			.placeOrder(order)
			.build();
	}
}
