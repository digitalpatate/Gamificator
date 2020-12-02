package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.BadgeAward;
import org.springframework.data.repository.CrudRepository;

public interface BadgeRewardRepository extends CrudRepository<BadgeAward, Long> {
}
