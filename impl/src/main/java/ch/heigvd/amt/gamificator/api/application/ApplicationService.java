package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.exceptions.NotAuthorizedException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ch.heigvd.amt.gamificator.entities.Application.toEntity;

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

    public List<ApplicationDTO> getAllApplication() {
        Iterable<Application> applications = this.applicationRepository.findAll();

        List<ApplicationDTO> applicationReads = new LinkedList<>();

        for(Application app : applications){
            applicationReads.add(Application.toDTO(app));
        }

        return applicationReads;

    }

    public ApplicationDTO getById(Long id) throws NotFoundException {

        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        return application.toDTO(application);
    }

    public long hasGoodCredential(String key, String secret) throws NotFoundException, NotAuthorizedException {
        Application application = this.applicationRepository.findByKey(key).orElseThrow(() -> new NotFoundException("Not found"));

        if(!application.getSecret().equals(secret)) {
            throw new NotAuthorizedException("Not authorized");
        }

        return application.getId();
    }

    public void deleteById(Long id) {
        try {
            applicationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            // Do not leak what application exists or not
        }
    }

    public ApplicationDTO updateById(Long id, ApplicationCreateCommand applicationCreate) {
        Application application = toEntity(applicationCreate);
        application.setId(id);

        applicationRepository.save(application);

        return application.toDTO(application);
    }

    public boolean canBeAuthenticated(String[] creds) {
        Optional<Application> oApplication = applicationRepository.findByKey(creds[0]);
        if(oApplication.isEmpty()){
            return false;
        }
        return oApplication.get().getSecret().equals(creds[1]);
    }
}
