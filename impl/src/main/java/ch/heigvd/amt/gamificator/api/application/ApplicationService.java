package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

        String key = creds[0];
        String url = creds[1];
        String signature = creds[2];



        Optional<Application> oApplication = applicationRepository.findByKey(key);
        if(oApplication.isEmpty()){
            return false;
        }

        String data = String.format("%s%s",key,url);


        String hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, oApplication.get().getSecret()).hmacHex(data);
        String calculatedSignature = Base64.getEncoder().encodeToString(hmac.getBytes(StandardCharsets.UTF_8));

        return signature.equals(calculatedSignature);
    }

    public long getApplicationIdFromApiKey(String apiKey) throws NotFoundException {
        Application application = applicationRepository.findByKey(apiKey)
                .orElseThrow(() -> new NotFoundException("Api key not found"));

        return application.getId();
    }
}