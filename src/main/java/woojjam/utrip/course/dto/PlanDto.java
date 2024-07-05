package woojjam.utrip.course.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import woojjam.utrip.place.dto.PlaceDto;

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
