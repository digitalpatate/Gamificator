package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationRegistrationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static ch.heigvd.amt.gamificator.util.StringGenerator.generateRandomString;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationRegistrationDTO registerNewApplication(ApplicationCreate applicationCreate) {

        Application newApplication = toEntity(applicationCreate);

        newApplication = applicationRepository.save(newApplication);

        ApplicationRegistrationDTO applicationRegistrationDTO = new ApplicationRegistrationDTO();

        applicationRegistrationDTO.setKey(newApplication.getKey());
        applicationRegistrationDTO.secret(newApplication.getSecret());
        applicationRegistrationDTO.setId((int) newApplication.getId());

        return  applicationRegistrationDTO;
    }

    public Application toEntity(ApplicationCreate applicationCreate) {

        UUID key = UUID.randomUUID();;
        String secret = generateRandomString(10);

        Application application =  new Application();

        application.setName(applicationCreate.getName());
        application.setUrl(applicationCreate.getUrl().toString());
        application.setKey(key.toString());
        application.setSecret(secret);

        return application;
    }
}
