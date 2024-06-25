package CodeIt.utrip.admin;

import lombok.Builder;
import lombok.Data;

import java.util.List;

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
