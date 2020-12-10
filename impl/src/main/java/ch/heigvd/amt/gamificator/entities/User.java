package ch.heigvd.amt.gamificator.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class User {

    @EmbeddedId
    UserId userId;

    @ManyToOne
    @MapsId("applicationId")
    private Application application;
}
