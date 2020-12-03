package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Badge;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BadgeRepository extends CrudRepository<Badge, Long> {

    Optional<Badge> findByNameAndApplicationId(String name, Long applicationId);

    Iterable<Badge> findByApplicationId(Long applicationId);

    Optional<Badge> findByIdAndApplicationId(Long id, Long applicationId);
}
