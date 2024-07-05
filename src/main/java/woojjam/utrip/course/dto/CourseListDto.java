package woojjam.utrip.course.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseListDto {

	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<PlanDto> plan;

	public static CourseListDto of(String name, LocalDateTime createdAt, LocalDateTime updatedAt,
		List<PlanDto> planDto) {
		return CourseListDto.builder()
			.name(name)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.plan(planDto)
			.build();
	}
}
