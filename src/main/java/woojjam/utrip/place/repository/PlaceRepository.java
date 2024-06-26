package woojjam.utrip.place.repository;

import woojjam.utrip.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPxAndPy(double posX, double posY);
    List<Place> findByIdIn(List<Long> ids);
}
