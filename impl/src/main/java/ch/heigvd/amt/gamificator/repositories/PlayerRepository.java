package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findByUUID(String uuid);
}
