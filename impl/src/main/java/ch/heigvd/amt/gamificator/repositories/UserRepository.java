package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findAllByApplicationId(long applicationId);
    Optional<User> findByUserId(UserId userId);
}
