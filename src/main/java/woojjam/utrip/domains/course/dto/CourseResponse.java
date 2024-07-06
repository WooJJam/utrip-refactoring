package woojjam.utrip.domains.course.dto;

import lombok.Builder;
import lombok.Data;
import woojjam.utrip.domains.place.dto.PlaceDto;

@Data
@Builder
public class CourseResponse {
	private PlaceDto course;

	public static CourseResponse from(PlaceDto placeDto) {
		return CourseResponse.builder()
			.course(placeDto)
			.build();
	}
}
