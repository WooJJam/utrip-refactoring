package woojjam.utrip.domains.user.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.common.domain.BaseEntity;
import woojjam.utrip.domains.course.domain.UserCourse;
import woojjam.utrip.domains.like.domain.ReviewLike;
import woojjam.utrip.domains.like.domain.VideoLike;
import woojjam.utrip.domains.review.domain.Review;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;
	private String nickname;
	private String email;
	private String password;
	private String refreshToken;
	private String role;

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<ReviewLike> reviewLikes = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<VideoLike> videoLikes = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@Builder.Default
	private List<UserCourse> userCourses = new ArrayList<>();

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void changePassword(String password) {
		this.password = password;
	}
}
