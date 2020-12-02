package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.BadgeAward;
import ch.heigvd.amt.gamificator.entities.PointsAward;
import org.springframework.data.repository.CrudRepository;

public interface PointsRewardRepository extends CrudRepository<PointsAward, Long> {
}
