package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.CreateEventCommand;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    private Date timestamp;
    private String type;

    public static Event toEntity(CreateEventCommand createEventCommand) {
        Event event = new Event();
        //event.setTimestamp(createEventCommand.getTimestamp());
        event.setType(createEventCommand.getType());

        return event;
    }
}
