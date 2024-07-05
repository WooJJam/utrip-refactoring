package woojjam.utrip.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place {

	@Id
	@GeneratedValue
	@Column(name = "place_id")
	private Long id;
	private String name;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String img;

	private double px;
	private double py;

	public static Place of(String name, String description, String img, double px, double py) {
		return Place.builder()
			.name(name)
			.description(description)
			.img(img)
			.px(px)
			.py(py)
			.build();
	}
}
