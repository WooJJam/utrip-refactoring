package CodeIt.utrip.course.domain;

import CodeIt.utrip.video.domain.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VideoCourse {

    @Id @GeneratedValue
    @Column(name = "video_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    private String places;
}
