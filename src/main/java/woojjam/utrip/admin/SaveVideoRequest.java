package woojjam.utrip.admin;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveVideoRequest {

	private String title;
	private String content;
	private String imageUrl;
	private String videoUrl;
	private int likeCount = 0;
	private List<String> tag;
	private List<PlaceAdminDto> place;
}
