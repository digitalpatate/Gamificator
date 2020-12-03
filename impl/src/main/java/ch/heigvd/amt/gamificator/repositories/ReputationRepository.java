package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Reputation;
import ch.heigvd.amt.gamificator.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReputationRepository extends CrudRepository<Reputation, Long> {
    Optional<Reputation> findByUser(User user);
}
