package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.UUID;

import static ch.heigvd.amt.gamificator.util.StringGenerator.generateRandomString;

public class ApplicationMapper {

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
