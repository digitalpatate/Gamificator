package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static ch.heigvd.amt.gamificator.util.StringGenerator.generateRandomString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String url;
    private String key;
    private String secret;


    public static Application toEntity(ApplicationCreateCommand command) {

        UUID key = UUID.randomUUID();;
        String secret = generateRandomString(10);

        Application application =  new Application();

        application.setName(command.getName());
        application.setUrl(command.getUrl().toString());
        application.setKey(key.toString());
        application.setSecret(secret);

        return application;
    }
}
