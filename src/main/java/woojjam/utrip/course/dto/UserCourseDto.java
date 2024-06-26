package woojjam.utrip.course.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
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
