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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ch.heigvd.amt.gamificator.api.application.ApplicationMapper.toDTO;
import static ch.heigvd.amt.gamificator.api.application.ApplicationMapper.toEntity;

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

    public ApplicationDTO getApplicationById(Long id) throws NotFoundException {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found"));

        return toDTO(application);
    }

    public ApplicationDTO updateById(Long id, ApplicationCreateCommand applicationCreate) {
        Application application = toEntity(applicationCreate);
        application.setId(id);

        applicationRepository.save(application);

        return toDTO(application);
    }

    public boolean canBeAuthenticated(String[] creds) {
        Optional<Application> oApplication = applicationRepository.findByKey(creds[0]);
        if(oApplication.isEmpty()){
            return false;
        }
        return oApplication.get().getSecret().equals(creds[1]);
    }

    public long getApplicationIdFromApiKey(String apiKey) throws NotFoundException {
        Application application = applicationRepository.findByKey(apiKey)
                .orElseThrow(() -> new NotFoundException("Api key not found"));

        return application.getId();
    }
}
