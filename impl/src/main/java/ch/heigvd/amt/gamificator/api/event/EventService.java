package ch.heigvd.amt.gamificator.api.event;

import ch.heigvd.amt.gamificator.api.application.ApplicationMapper;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.EventRepository;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import ch.heigvd.amt.gamificator.services.EventProcessor;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EventService {

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private ApplicationService applicationService;

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventProcessor eventProcessor;

    public void createEvent(CreateEventCommand createEventCommand) throws NotFoundException {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        Event event = EventMapper.toEntity(createEventCommand);
        UserId userId = new UserId();
        userId.setUUID(createEventCommand.getUserUUID().toString());
        userId.setApplicationId(applicationId);

        Optional<User> oUser = userRepository.findByUserId(userId);

        User user = new User();

        if(oUser.isEmpty()){
            user.setUserId(userId);
            ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
            user.setApplication(ApplicationMapper.toEntity(applicationDTO));
            userRepository.save(user);
        } else {
            user =  oUser.get();
        }

        event.setUser(user);

        eventProcessor.process(event, applicationId);
        eventRepository.save(event);
    }

}
