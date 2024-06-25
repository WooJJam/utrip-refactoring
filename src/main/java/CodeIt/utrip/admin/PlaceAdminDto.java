package CodeIt.utrip.admin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceAdminDto {
    private String name;
    private String description;
    private String img;
    private double posX;
    private double posY;
}
