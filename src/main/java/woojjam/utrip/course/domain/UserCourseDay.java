package woojjam.utrip.course.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCourseDay {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_course_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserCourse userCourse;

    private int dayNum;

}
