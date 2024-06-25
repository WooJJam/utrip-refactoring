package CodeIt.utrip.place.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place {

    @Id @GeneratedValue
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
}
