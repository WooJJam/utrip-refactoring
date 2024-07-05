package woojjam.utrip.like;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import woojjam.utrip.like.domain.ReviewLike;
import woojjam.utrip.like.domain.VideoLike;
import woojjam.utrip.review.domain.Review;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.video.domain.Video;

@SpringBootTest
@Transactional
public class LikeTest {

	@PersistenceContext
	EntityManager em;

	@Test
	public void 리뷰_좋아요_저장() throws Exception {
		User user = User.builder()
			.email("email")
			.nickname("nickname")
			.build();

		em.persist(user);

		Review review = Review.builder()
			.content("Review Test")
			.build();
		em.persist(review);

		ReviewLike reviewLike = ReviewLike.builder()
			.review(review)
			.user(user)
			.build();
		em.persist(reviewLike);

		Assertions.assertThat(reviewLike).isEqualTo(em.find(ReviewLike.class, reviewLike.getId()));
	}

	@Test
	public void 비디오_좋아요_저장() throws Exception {
		//given
		User user = User.builder()
			.email("email")
			.nickname("nickname")
			.build();

		em.persist(user);

		Video video = Video.builder()
			.content("Review Test")
			.build();
		em.persist(video);

		VideoLike videoLike = VideoLike.builder()
			.video(video)
			.user(user)
			.build();
		em.persist(videoLike);

		//when
		VideoLike find = em.find(VideoLike.class, videoLike.getId());

		//then
		Assertions.assertThat(find).isEqualTo(videoLike);

	}
}
