package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    @SneakyThrows
    public static ApplicationDTO toDTO(Application application) {

        ApplicationDTO applicationDTO = new ApplicationDTO();

        applicationDTO.setId((int)application.getId());
        applicationDTO.setName(application.getName());
        applicationDTO.setUrl(new URI(application.getUrl()));
        return applicationDTO;
    }

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

    public static Application toEntity(ApplicationDTO dto) {
        Application application =  new Application();

        application.setId(dto.getId());
        application.setName(dto.getName());
        application.setUrl(dto.getUrl().toString());

        return application;
    }
}
