package ch.heigvd.amt.gamificator.api.pointScale;
import ch.heigvd.amt.gamificator.api.PointScalesApi;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PointScaleController implements PointScalesApi {

    @Autowired
    private PointScaleService pointScaleService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public ResponseEntity<PointScaleDTO> createPointScale(@Valid @RequestBody PointScaleCreateCommand pointScaleCreateCommand) {
        PointScaleDTO pointScaleDTO = pointScaleService.createPointScale(pointScaleCreateCommand);

        long applicationId = pointScaleCreateCommand.getApplicationId();

        //if(applicationService. )

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePointScale(@PathVariable Long id) {
        pointScaleService.deletePointScaleById(id);
        // TODO: implement this
        return null;
    }

    @Override
    public ResponseEntity<List<PointScaleDTO>> getAllPointScales(@Valid @RequestParam(value = "applicationId", required = false) Long applicationId) {
        List<PointScaleDTO> pointScaleDTOs = pointScaleService.getAllPointScales();
        return new ResponseEntity<>(pointScaleDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PointScaleDTO> getPointScale(@PathVariable Long id) {
        // TODO: implement this
        return null;
    }

    @Override
    public ResponseEntity<PointScaleDTO> updatePointScale(@PathVariable Long id, @Valid PointScaleCreateCommand pointScaleCreateCommand) {
        // TODO: implement this
        return null;
    }
}
