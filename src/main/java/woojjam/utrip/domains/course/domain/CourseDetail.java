package woojjam.utrip.domains.course.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woojjam.utrip.domains.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetail {

	@Id
	@GeneratedValue
	@Column(name = "course_deatil_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_course_day_id")
	private UserCourseDay userCourseDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place")
	private Place place;

	private int placeOrder;
}
