package woojjam.utrip.user.domain;

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
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.like.domain.ReviewLike;
import woojjam.utrip.like.domain.VideoLike;
import woojjam.utrip.review.domain.Review;

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

	public static User of(String nickname, String email, String password, String refreshToken) {
		return User.builder()
			.nickname(nickname)
			.email(email)
			.password(password)
			.refreshToken(refreshToken)
			.build();
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void changePassword(String password) {
		this.password = password;
	}
}
