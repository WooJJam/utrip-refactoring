package woojjam.utrip.course.dto;

import lombok.*;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.UserCourse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CourseDetailDto {

    private String places;
    private UserCourse userCourse;
    private int dayNum;

    public static CourseDetail toEntity(String places, UserCourse userCourse, int dayNum) {
        return CourseDetail.builder()
                .places(places)
                .userCourse(userCourse)
                .dayNum(dayNum)
                .build();
    }
}
