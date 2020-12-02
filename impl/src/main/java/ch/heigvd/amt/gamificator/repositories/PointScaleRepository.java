package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.PointScale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PointScaleRepository extends CrudRepository<PointScale, Long> {
    List<PointScale> findByApplicationId(Long applicationId);

    Optional<PointScale> findByName(String name);
}
