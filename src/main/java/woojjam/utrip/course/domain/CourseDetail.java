package woojjam.utrip.course.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.place.domain.Place;

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
    @JoinColumn(name = "user_course_day_id")
    private UserCourseDay userCourseDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place")
    private Place place;

    private int placeOrder;
}
