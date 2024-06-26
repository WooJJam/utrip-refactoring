package woojjam.utrip.course.dto;

import woojjam.utrip.place.dto.PlaceDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlanDto {

    private int day;
    private List<PlaceDto> place;

    public static PlanDto of(int day, List<PlaceDto> place) {
        return PlanDto.builder()
                .day(day)
                .place(place)
                .build();
    }
}
