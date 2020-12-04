package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Reward;
import ch.heigvd.amt.gamificator.entities.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RewardRepository extends CrudRepository<Reward, Long> {
    List<Reward> findByRule(Rule rule);
}
