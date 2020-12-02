package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.BadgeReward;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRewardRepository extends CrudRepository<BadgeReward, Long> {

    List<BadgeReward> findAllByRuleId(Long ruleId);
}
