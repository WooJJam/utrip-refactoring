package woojjam.utrip.review.dto;

import woojjam.utrip.review.domain.Review;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Data
@Builder
public class ReviewDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private int score;
    private LocalDateTime createdAt;

    public static ReviewDto from(Review review, String nickname) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .nickname(nickname)
                .content(review.getContent())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
