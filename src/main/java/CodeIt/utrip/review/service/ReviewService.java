package CodeIt.utrip.review.service;

import CodeIt.utrip.common.dto.BasePageDto;
import CodeIt.utrip.common.exception.NoSuchElementException;
import CodeIt.utrip.common.exception.UserException;
import CodeIt.utrip.common.reponse.StatusCode;
import CodeIt.utrip.common.reponse.SuccessResponse;
import CodeIt.utrip.review.domain.Review;
import CodeIt.utrip.review.dto.ReviewDto;
import CodeIt.utrip.review.dto.ReviewPageResponse;
import CodeIt.utrip.review.dto.SaveReviewDto;
import CodeIt.utrip.review.repository.ReviewRepository;
import CodeIt.utrip.user.domain.User;
import CodeIt.utrip.user.repository.UserRepository;
import CodeIt.utrip.video.domain.Video;
import CodeIt.utrip.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, VideoRepository videoRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

   @Transactional(readOnly = true)
    public ResponseEntity<?> getReviewList(Long videoId, String sort, int page) {
        Pageable pageable= PageRequest.of(page, 10);
        Page<Review> reviews = null;

        if (sort.equals("score")) {
            reviews = reviewRepository.findByVideoIdOrderByScoreDesc(videoId, pageable);
        }

        if (sort.equals("latest")) {
            reviews = reviewRepository.findByVideoIdOrderByCreatedAtDesc(videoId, pageable);
        }

        if (reviews == null || reviews.isEmpty()) {
            System.out.println("reviews is null or empty");
            throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
        }

       Page<ReviewDto> reviewDtoPage = reviews.map(review -> {
           String nickname = userRepository.findById(review.getUser().getId()).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND)).getNickname();
           return ReviewDto.from(review, nickname);
       });
        BasePageDto<ReviewDto> basePageDto = BasePageDto.from(reviewDtoPage);

        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), ReviewPageResponse.of(reviewDtoPage.getContent(), basePageDto)));
    }

    public ResponseEntity<?> saveReview(Long videoId, String email, SaveReviewDto saveReviewDto) {
        User user = findUserByEmail(email);
        Video video = findVideoById(videoId);

        Review review = Review.of(user, video, saveReviewDto);
        log.info("Review = {}", review);
        reviewRepository.save(review);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));
    }

    private Video findVideoById(Long videoId) {
        return videoRepository.findById(videoId).orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND));
    }
    public ResponseEntity<?> patchReview(Long videoId, Long reviewId, String email, SaveReviewDto saveReviewDto) {
        User user = findUserByEmail(email);
        Video video = findVideoById(videoId);
        Review review = findReviewById(reviewId);

        if (!review.getVideo().getId().equals(videoId)) {
            throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
        }

        review.update(saveReviewDto);
        reviewRepository.save(review);

        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    public ResponseEntity<?> deleteReview(Long videoId, Long reviewId, String email) {
        User user = findUserByEmail(email);
        Video video = findVideoById(videoId);
        Review review = findReviewById(reviewId);

        if (!review.getVideo().getId().equals(videoId)) {
            throw new NoSuchElementException(StatusCode.REVIEW_NOT_FOUND);
        }

        reviewRepository.delete(review);

        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

}
