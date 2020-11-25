package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.ApplicationsApi;
import ch.heigvd.amt.gamificator.api.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Log
public class ApplicationController implements ApplicationsApi {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<ApplicationCreateDTO> createApplication(@Valid @RequestBody ApplicationCreateCommand applicationCreate) {

        log.info(String.format("POST /applications with [%s]",applicationCreate));

        ApplicationCreateDTO applicationRegistrationDTO = applicationService.create(applicationCreate);

        return new ResponseEntity(applicationRegistrationDTO,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApplicationDTO> getApplication() {
        //TODO: Wait for Guillaume
        return null;
    }


    @Override
    public ResponseEntity<Void> updateApplication(@PathVariable Long id, @Valid ApplicationCreateCommand applicationCreate) {
        log.info(String.format("PUT /applications/:id with id : %s",id));

        ApplicationDTO updatedApplication = applicationService.updateById(id,applicationCreate);

        return new ResponseEntity(updatedApplication,HttpStatus.OK);
    }
}
