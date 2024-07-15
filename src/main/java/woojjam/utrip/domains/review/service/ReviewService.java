package woojjam.utrip.domains.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.dto.BasePageDto;
import woojjam.utrip.common.exception.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.review.domain.Review;
import woojjam.utrip.domains.review.dto.ReviewDto;
import woojjam.utrip.domains.review.dto.ReviewPageResponse;
import woojjam.utrip.domains.review.dto.SaveReviewDto;
import woojjam.utrip.domains.review.repository.ReviewRepository;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.exception.UserErrorCode;
import woojjam.utrip.domains.user.exception.UserException;
import woojjam.utrip.domains.user.repository.UserRepository;
import woojjam.utrip.domains.video.domain.Video;
import woojjam.utrip.domains.video.repository.VideoRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final VideoRepository videoRepository;

	@Transactional(readOnly = true)
	public ResponseEntity<?> getReviewList(Long videoId, String sort, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		Page<Review> reviews = null;

		if (sort.equals("score")) {
			reviews = reviewRepository.findByVideoIdOrderByScoreDesc(videoId, pageable);
		}

		if (sort.equals("latest")) {
			reviews = reviewRepository.findByVideoIdOrderByCreatedAtDesc(videoId, pageable);
		}

		// if (reviews == null || reviews.isEmpty()) {
		// 	log.error("reviews is null or empty");
		// 	throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
		// }

		Page<ReviewDto> reviewDtoPage = reviews.map(review -> {
			String nickname = userRepository.findById(review.getUser().getId())
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND))
				.getNickname();
			return ReviewDto.from(review, nickname);
		});
		BasePageDto<ReviewDto> basePageDto = BasePageDto.from(reviewDtoPage);

		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), "StatusCode.SUCCESS.getMessage()",
			ReviewPageResponse.of(reviewDtoPage.getContent(), basePageDto)));
	}

	public ResponseEntity<?> saveReview(Long videoId, String email, SaveReviewDto saveReviewDto) {
		User user = findUserByEmail(email);
		Video video = findVideoById(videoId);

		Review review = Review.of(user, video, saveReviewDto);
		log.info("Review = {}", review);
		reviewRepository.save(review);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}

	private User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
	}

	private Video findVideoById(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
	}

	private Review findReviewById(Long reviewId) {
		return reviewRepository.findById(reviewId).get();
		// .orElseThrow(() -> new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND));
	}

	public ResponseEntity<?> patchReview(Long videoId, Long reviewId, SaveReviewDto saveReviewDto) {
		Review review = findReviewById(reviewId);

		// if (!review.getVideo().getId().equals(videoId)) {
		// 	throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
		// }

		review.update(saveReviewDto);

		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}

	public ResponseEntity<?> deleteReview(Long videoId, Long reviewId) {
		Review review = findReviewById(reviewId);
		// if (!review.getVideo().getId().equals(videoId)) {
		// 	throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
		// }

		reviewRepository.delete(review);

		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}

}
