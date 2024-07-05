package woojjam.utrip.domains.course.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {

	private Long id;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<PlanDto> plan;

	public static CourseDto of(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
		List<PlanDto> planDto) {
		return CourseDto.builder()
			.id(id)
			.name(name)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.plan(planDto)
			.build();
	}
}
