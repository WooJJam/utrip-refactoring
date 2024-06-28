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
    @JoinColumn(name = "user_course_id")
    private UserCourse userCourse;

    @OneToOne(fetch = FetchType.LAZY)
    private Place place;

    private int dayNum;
    private int placeOrder;
}
