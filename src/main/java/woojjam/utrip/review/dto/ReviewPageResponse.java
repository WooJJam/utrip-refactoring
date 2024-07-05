package woojjam.utrip.review.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import woojjam.utrip.common.dto.BasePageDto;

@Data
@Getter
@Builder
public class ReviewPageResponse<T> {
	private List<T> content;
	private BasePageDto<T> pageInfo;

	public static <T> ReviewPageResponse<T> of(List<T> content, BasePageDto<T> pageInfo) {
		return ReviewPageResponse.<T>builder()
			.content(content)
			.pageInfo(pageInfo)
			.build();
	}
}
