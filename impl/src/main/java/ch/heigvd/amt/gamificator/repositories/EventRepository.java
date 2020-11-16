package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.Rule;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
