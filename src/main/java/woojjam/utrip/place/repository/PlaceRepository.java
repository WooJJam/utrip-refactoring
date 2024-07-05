package woojjam.utrip.place.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woojjam.utrip.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	Optional<Place> findByPxAndPy(double posX, double posY);

	List<Place> findByPxInAndPyIn(List<Double> posXs, List<Double> posYs);

	List<Place> findByIdIn(List<Long> ids);
}
