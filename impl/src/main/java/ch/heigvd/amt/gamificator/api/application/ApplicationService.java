package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateDTO;
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

    public ApplicationCreateDTO create(ApplicationCreateCommand applicationCreate) {

        Application newApplication = toEntity(applicationCreate);

        newApplication = applicationRepository.save(newApplication);

        log.info(newApplication.toString());

        ApplicationCreateDTO applicationRegistrationDTO = new ApplicationCreateDTO();

        applicationRegistrationDTO.setKey(newApplication.getKey());
        applicationRegistrationDTO.secret(newApplication.getSecret());
        applicationRegistrationDTO.setId((int) newApplication.getId());

        return  applicationRegistrationDTO;
    }

    @SneakyThrows
    public ApplicationDTO toDTO(Application application) {

        ApplicationDTO applicationRead = new ApplicationDTO();

        applicationRead.setId((int)application.getId());
        applicationRead.setName(application.getName());
        applicationRead.setUrl(new URI(application.getUrl()));
        return applicationRead;
    }

    public List<ApplicationDTO> getAllApplication() {
        Iterable<Application> applications = this.applicationRepository.findAll();


        List<ApplicationDTO> applicationReads = new LinkedList<>();

        for(Application app : applications){
            applicationReads.add(toDTO(app));
        }

        return applicationReads;

    }

    public ApplicationDTO getById(Long id) throws NotFoundException {

        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        return toDTO(application);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public ApplicationDTO updateById(Long id, ApplicationCreateCommand applicationCreate) {
        Application application = toEntity(applicationCreate);
        application.setId(id);

        applicationRepository.save(application);

        return toDTO(application);
    }
}
