package woojjam.utrip.domains.video.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import woojjam.utrip.domains.video.domain.Video;

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
