package CodeIt.utrip.user.dto;

import CodeIt.utrip.course.dto.CourseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
