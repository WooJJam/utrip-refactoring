package woojjam.utrip.domains.video.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.common.domain.BaseEntity;
import woojjam.utrip.domains.like.domain.VideoLike;
import woojjam.utrip.domains.place.domain.Place;
import woojjam.utrip.domains.review.domain.Review;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Video extends BaseEntity {

	@Id
	@GeneratedValue
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
