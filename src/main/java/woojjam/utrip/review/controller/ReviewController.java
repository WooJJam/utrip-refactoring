package woojjam.utrip.review.controller;

import woojjam.utrip.common.JwtUtils;
import woojjam.utrip.review.dto.SaveReviewDto;
import woojjam.utrip.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtils jwtUtils;

    @GetMapping("/video/{video-id}/reviews")
    public ResponseEntity<?> getReviewList(
            @PathVariable("video-id") Long videoId,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page
    ) {
        return reviewService.getReviewList(videoId, sort, page);
    }

    @PostMapping("/video/{video_id}/review")
    public ResponseEntity<?> saveReview(@PathVariable("video_id") Long videoId,
                                        @RequestBody SaveReviewDto saveReviewDto,
                                        HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = jwtUtils.splitBearerToken(bearerToken);
        String email = (String) jwtUtils.getClaims(token).get("email");
        return reviewService.saveReview(videoId, email, saveReviewDto);
    }

    @PatchMapping("/video/{video_id}/review/{review_id}")
    public ResponseEntity<?> patchReview(
            @PathVariable("video_id") Long videoId,
            @PathVariable("review_id") Long reviewId,
            @RequestBody SaveReviewDto saveReviewDto) {
        return reviewService.patchReview(videoId, reviewId, saveReviewDto);
    }

    @DeleteMapping("/video/{video-id}/review/{review-id}")
    public ResponseEntity<?> deleteReview(@PathVariable("video-id") Long videoId,
                                          @PathVariable("review-id") Long reviewId) {
        return reviewService.deleteReview(videoId, reviewId);
    }

}
