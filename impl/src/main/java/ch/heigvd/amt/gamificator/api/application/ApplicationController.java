package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.ApplicationsApi;
import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Log
public class ApplicationController implements ApplicationsApi {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<ApplicationCreateDTO> createApplication(@Valid @RequestBody ApplicationCreateCommand applicationCreate) {

        log.info(String.format("POST /applications with [%s]",applicationCreate));

        ApplicationRegistrationDTO applicationRegistrationDTO = applicationService.create(applicationCreate);

        return new ResponseEntity(applicationRegistrationDTO,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {

        log.info(String.format("DELETE /applications/:id with id : %s",id));

        applicationService.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ApplicationDTO>> getAllApplication() {

        log.info(String.format("GET /applications"));

        List<ApplicationRead> application = applicationService.getAllApplication();

        return new ResponseEntity(application, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.info(String.format("GET /applications/:id with id : %s",id));


        try {
            ApplicationRead application = applicationService.getById(id);

            return new ResponseEntity(application, HttpStatus.OK);

        } catch (ApiException e) {
            log.info(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> updateApplication(@PathVariable Long id, @Valid ApplicationCreateCommand applicationCreate) {
        log.info(String.format("PUT /applications/:id with id : %s",id));

        ApplicationRead updatedApplication = applicationService.updateById(id,applicationCreate);

        return new ResponseEntity(updatedApplication,HttpStatus.OK);
    }
}
