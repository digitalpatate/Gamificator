package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationRead;
import ch.heigvd.amt.gamificator.api.model.ApplicationRegistrationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static ch.heigvd.amt.gamificator.entities.Application.toEntity;
import static ch.heigvd.amt.gamificator.util.StringGenerator.generateRandomString;

@Service
@AllArgsConstructor
@Log
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationRegistrationDTO create(ApplicationCreateCommand applicationCreate) {

        Application newApplication = toEntity(applicationCreate);

        newApplication = applicationRepository.save(newApplication);

        log.info(newApplication.toString());

        ApplicationRegistrationDTO applicationRegistrationDTO = new ApplicationRegistrationDTO();

        applicationRegistrationDTO.setKey(newApplication.getKey());
        applicationRegistrationDTO.secret(newApplication.getSecret());
        applicationRegistrationDTO.setId((int) newApplication.getId());

        return  applicationRegistrationDTO;
    }

    @SneakyThrows
    public ApplicationRead toDTO(Application application) {

        ApplicationRead applicationRead = new ApplicationRead();

        applicationRead.setId((int)application.getId());
        applicationRead.setName(application.getName());
        applicationRead.setUrl(new URI(application.getUrl()));
        return applicationRead;
    }

    public List<ApplicationRead> getAllApplication() {
        Iterable<Application> applications = this.applicationRepository.findAll();


        List<ApplicationRead> applicationReads = new LinkedList<>();

        for(Application app : applications){
            applicationReads.add(toDTO(app));
        }

        return applicationReads;

    }

    public ApplicationRead getById(Long id) throws NotFoundException {

        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException(404,"Not found"));

        return toDTO(application);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public ApplicationRead updateById(Long id, ApplicationCreateCommand applicationCreate) {
        Application application = toEntity(applicationCreate);
        application.setId(id);

        applicationRepository.save(application);

        return toDTO(application);
    }
}
