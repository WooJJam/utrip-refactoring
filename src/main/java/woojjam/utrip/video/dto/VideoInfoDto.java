package woojjam.utrip.video.dto;

import woojjam.utrip.video.domain.Video;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
public class VideoInfoDto {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private String imageUrl;
    private List<String> tags;
    private int likeCount;
    private LocalDateTime createdAt;


    public static VideoInfoDto fromEntity(Video video) {
        return VideoInfoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .content(video.getContent())
                .videoUrl(video.getVideoUrl())
                .imageUrl(video.getImageUrl())
                .tags(video.getTags())
                .likeCount(video.getLikeCount())
                .createdAt(video.getCreatedAt())
                .build();
    }
}
