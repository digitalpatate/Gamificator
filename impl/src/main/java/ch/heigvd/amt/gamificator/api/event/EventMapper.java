package ch.heigvd.amt.gamificator.api.event;

import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import ch.heigvd.amt.gamificator.entities.Event;

import java.time.OffsetDateTime;

public class EventMapper {
    public static Event toEntity(CreateEventCommand createEventCommand) {
        Event event = new Event();
        event.setTimestamp(createEventCommand.getTimestamp());
        event.setType(createEventCommand.getType());

        return event;
    }
}
