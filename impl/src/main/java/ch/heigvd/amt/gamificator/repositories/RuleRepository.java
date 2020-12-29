package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<Rule, Long> {
    List<Rule> findAllByApplicationId(long applicationId);

    void deleteByIdAndApplicationId(Long id, Long applicationId);
}
