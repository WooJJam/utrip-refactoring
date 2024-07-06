package woojjam.utrip.domains.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.video.domain.Video;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class VideoLike {

	@Id
	@GeneratedValue
	@Column(name = "video_like_Id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id")
	private Video video;

	public static VideoLike from(User user, Video video) {
		return VideoLike.builder()
			.user(user)
			.video(video)
			.build();
	}
}