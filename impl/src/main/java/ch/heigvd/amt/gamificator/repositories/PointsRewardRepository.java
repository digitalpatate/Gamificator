package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.BadgeReward;
import ch.heigvd.amt.gamificator.entities.PointsReward;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointsRewardRepository extends RewardRepository<PointsReward> {

    List<PointsReward> findAllByRuleId(Long ruleId);
}
