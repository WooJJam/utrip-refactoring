package woojjam.utrip.place.domain;

import jakarta.persistence.*;
import lombok.*;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.VideoCourse;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place {

    @Id @GeneratedValue
    @Column(name = "place_id")
    private Long id;
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String img;

    private double px;
    private double py;

    @OneToOne(mappedBy = "place")
    private CourseDetail courseDetail;

    @OneToOne(mappedBy = "place")
    private VideoCourse videoCourse;
}
