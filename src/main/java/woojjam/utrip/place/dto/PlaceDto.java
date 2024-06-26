package woojjam.utrip.place.dto;

import woojjam.utrip.place.domain.Place;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceDto {
    private int index;
    private String name;
    private String description;
    private String img;
    private double posX;
    private double posY;

    public static PlaceDto of(int index, Place place) {
        return PlaceDto.builder()
                .index(index)
                .name(place.getName())
                .description(place.getDescription())
                .img(place.getImg())
                .posX(place.getPx())
                .posY(place.getPy())
                .build();
    }

    public static Place toEntity(PlaceDto placeDto) {
        return Place.builder()
                .name(placeDto.getName())
                .px(placeDto.getPosX())
                .py(placeDto.getPosY())
                .img(placeDto.getImg())
                .description(placeDto.getDescription())
                .build();
    }
}
