package woojjam.utrip.like.domain;

import woojjam.utrip.user.domain.User;
import woojjam.utrip.video.domain.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class VideoLike {

    @Id @GeneratedValue
    @Column(name = "video_like_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    public VideoLike(Video video, User user) {
        this.video = video;
        this.user = user;
    }
}