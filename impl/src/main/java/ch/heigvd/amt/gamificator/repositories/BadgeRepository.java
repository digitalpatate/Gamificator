package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Badge;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BadgeRepository extends CrudRepository<Badge, Long> {

    Optional<Badge> findByName(String name);
}
