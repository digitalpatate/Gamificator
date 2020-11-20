package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRepository extends CrudRepository<PointScale, Long> {
    List<PointScale> findByApplicationId(Long applicationId);
}
