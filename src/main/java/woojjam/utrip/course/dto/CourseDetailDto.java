package woojjam.utrip.course.dto;

import lombok.*;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.course.domain.UserCourseDay;
import woojjam.utrip.place.domain.Place;

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
