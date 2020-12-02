package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Reputation;
import org.springframework.data.repository.CrudRepository;

public interface ReputationRepository extends CrudRepository<Reputation, Long> {
}
