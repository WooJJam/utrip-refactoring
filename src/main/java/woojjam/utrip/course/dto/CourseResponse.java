package woojjam.utrip.course.dto;

import woojjam.utrip.place.dto.PlaceDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseResponse {
    private List<PlaceDto> course;

    public static CourseResponse from(List<PlaceDto> placeDto) {
        return CourseResponse.builder()
                .course(placeDto)
                .build();
    }
}
