package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.ApplicationsApi;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ApplicationController implements ApplicationsApi {

    @Autowired
    private ApplicationService applicationService;



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
