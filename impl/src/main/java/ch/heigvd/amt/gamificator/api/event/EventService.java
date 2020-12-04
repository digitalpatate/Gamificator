package ch.heigvd.amt.gamificator.api.event;

import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.EventRepository;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import ch.heigvd.amt.gamificator.services.EventProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final EventProcessor eventProcessor;

    public void createEvent(@Valid CreateEventCommand createEventCommand) throws NotFoundException {
        Event event = Event.toEntity(createEventCommand);
        Optional<User> oUser = userRepository.findByUUID(createEventCommand.getUserUUID().toString());
        User user = new User();

        if(oUser.isEmpty()){
            user.setUUID(UUID.randomUUID());
            userRepository.save(user);
        }else{
            user =  oUser.get();
        }
        event.setUser(user);

        eventProcessor.process(event);

        eventRepository.save(event);
    }

}
