package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUUID(String UUID);
}
