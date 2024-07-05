package woojjam.utrip.domains.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveReviewDto {
	private String title;
	private String nickname;
	private String content;
	private int score;
}
