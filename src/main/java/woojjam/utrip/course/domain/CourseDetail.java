package woojjam.utrip.course.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {

    @Id @GeneratedValue
    @Column(name = "course_deatil_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id")
    private UserCourse userCourse;

    private String places;
    private int dayNum;

    public void updateCourseDetail(UserCourse userCourse, String places, int dayNum) {
        this.userCourse = userCourse;
        this.places = places;
        this.dayNum = dayNum;
    }
}
