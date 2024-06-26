package woojjam.utrip.video.dto;

import woojjam.utrip.video.domain.Video;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class VideoListDto {

    private Long id;
    private String title;
    private String videoUrl;
    private String imageUrl;
    private String content;
    private int likeCount;
    private List<String> tags;

    public static VideoListDto from(Video video) {
        return VideoListDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .videoUrl(video.getVideoUrl())
                .imageUrl(video.getImageUrl())
                .content(video.getContent())
                .tags(video.getTags())
                .likeCount(video.getLikeCount())
                .build();
    }
}
