package woojjam.utrip.domains.video.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import woojjam.utrip.domains.video.domain.Video;

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
