package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Reward;
import ch.heigvd.amt.gamificator.entities.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RewardRepository<T extends Reward> extends CrudRepository<T, Long> {
    List<Reward> findByRuleId(long ruleId);
}

