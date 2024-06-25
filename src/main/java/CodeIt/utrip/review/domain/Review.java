package CodeIt.utrip.review.domain;

import CodeIt.utrip.common.domain.BaseEntity;
import CodeIt.utrip.like.domain.ReviewLike;
import CodeIt.utrip.review.dto.SaveReviewDto;
import CodeIt.utrip.user.domain.User;
import CodeIt.utrip.video.domain.Video;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @OneToMany(mappedBy = "review")
    @Builder.Default
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    private String title;
    private String content;
    private int score;

    public static Review of(User user, Video video, String content) {
        return Review.builder()
                .user(user)
                .video(video)
                .content(content)
                .build();
    }

    public static Review of(User user, Video video, SaveReviewDto saveReviewDto) {
        return Review.builder()
                .user(user)
                .video(video)
                .content(saveReviewDto.getContent())
                .score(saveReviewDto.getScore())
                .title(saveReviewDto.getTitle())
                .build();
    }

    public void update(SaveReviewDto saveReviewDto) {
        this.title = saveReviewDto.getTitle();
        this.content = saveReviewDto.getContent();
        this.score = saveReviewDto.getScore();
    }

}
