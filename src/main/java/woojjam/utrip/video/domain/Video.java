package woojjam.utrip.video.domain;

import woojjam.utrip.common.domain.BaseEntity;
import woojjam.utrip.like.domain.VideoLike;
import woojjam.utrip.place.domain.Place;
import woojjam.utrip.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Video extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "video_id")
    private Long id;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String videoUrl;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "likes_count")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setLikecount(int likeCount) {
        this.likeCount = likeCount;
    }

    @ElementCollection
    @CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"))
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "video")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoLike> videoLikes = new ArrayList<>();

    public static Video of(String title, String content, String url, int likeCount, List<String> tags) {
        return Video.builder()
                .title(title)
                .content(content)
                .videoUrl(url)
                .likeCount(likeCount)
                .tags(tags)
                .build();
    }
}
