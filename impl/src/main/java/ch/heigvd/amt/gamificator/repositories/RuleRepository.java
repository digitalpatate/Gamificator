package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Rule;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<Rule, Long> {
    Iterable<Rule> findAllByEventType(String type);
}
