package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.ApplicationsApi;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationRead;
import ch.heigvd.amt.gamificator.api.model.ApplicationRegistrationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ApplicationController implements ApplicationsApi {

    @Autowired
    private ApplicationService applicationService;

    @Override
    public ResponseEntity<ApplicationRegistrationDTO> createApplication(@Valid @RequestBody ApplicationCreate applicationCreate) {

        ApplicationRegistrationDTO applicationRegistrationDTO = applicationService.registerNewApplication(applicationCreate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(applicationRegistrationDTO.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteApplication(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ApplicationRead>> getAllApplication() {
        return null;
    }

    @Override
    public ResponseEntity<ApplicationRead> getApplication(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateApplication(Integer id, @Valid ApplicationCreate applicationCreate) {
        return null;
    }
}
