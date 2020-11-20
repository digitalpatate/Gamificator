package ch.heigvd.amt.gamificator.api.event;

import ch.heigvd.amt.gamificator.api.EventsApi;
import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class EventController implements EventsApi {

    private final EventService eventService;

    @Override
    public ResponseEntity<Void> createEvent(@Valid CreateEventCommand createEventCommand) {

        try {
            eventService.createEvent(createEventCommand);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(),e.getCode());
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
