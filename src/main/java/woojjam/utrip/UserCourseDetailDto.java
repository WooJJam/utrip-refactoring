package woojjam.utrip;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserCourseDetailDto {
	Long courseId;
	String courseName;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	int day;
	int order;
	String placeName;
	String description;
	String image;
	double posX;
	double posY;

	public static UserCourseDetailDto of(String courseName, LocalDateTime createdAt, LocalDateTime updatedAt, int day) {
		return UserCourseDetailDto.builder()
			.courseName(courseName)
			.day(day)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}
}