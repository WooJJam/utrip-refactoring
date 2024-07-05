package woojjam.utrip.domains.review.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.common.domain.BaseEntity;
import woojjam.utrip.domains.like.domain.ReviewLike;
import woojjam.utrip.domains.review.dto.SaveReviewDto;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.video.domain.Video;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Review extends BaseEntity {

	@Id
	@GeneratedValue
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
