package CodeIt.utrip.review.dto;

import CodeIt.utrip.common.dto.BasePageDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

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
