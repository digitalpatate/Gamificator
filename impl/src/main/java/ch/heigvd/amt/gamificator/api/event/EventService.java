package ch.heigvd.amt.gamificator.api.event;

import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.Player;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.EventRepository;
import ch.heigvd.amt.gamificator.repositories.PlayerRepository;
import ch.heigvd.amt.gamificator.services.EventProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    private final EventProcessor eventProcessor;

    public void createEvent(@Valid CreateEventCommand createEventCommand) throws NotFoundException {
        Event event = Event.toEntity(createEventCommand);
        Player player = playerRepository.findByUUID(createEventCommand.getUserUUID()).orElseThrow(() -> new NotFoundException("Not found"));

        event.setPlayer(player);

        eventProcessor.process(event);

        eventRepository.save(event);
    }

}
