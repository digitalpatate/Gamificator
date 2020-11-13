package ch.heigvd.amt.gamificator.api.pointScale;
import ch.heigvd.amt.gamificator.api.PointScalesApi;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
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

    @Override
    public ResponseEntity<PointScaleDTO> createPointScale(@Valid @RequestBody PointScaleCreateCommand pointScaleCreateCommand) {
        PointScaleDTO pointScaleDTO = null;

        try {
            pointScaleDTO = pointScaleService.createPointScale(pointScaleCreateCommand);
        } catch (NotFoundException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePointScale(@PathVariable Long id) {
        pointScaleService.deletePointScaleById(id);

        // must check this PointScale exists ?

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<PointScaleDTO>> getAllPointScales(@Valid @RequestParam(value = "applicationId", required = false) Long applicationId) {
        List<PointScaleDTO> pointScaleDTOs = pointScaleService.getAllPointScales();
        return new ResponseEntity<>(pointScaleDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PointScaleDTO> getPointScale(@PathVariable Long id) {
        PointScaleDTO pointScaleDTO = null;

        try {
            pointScaleDTO = pointScaleService.getPointScaleById(id);
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PointScaleDTO> updatePointScale(@PathVariable Long id, @Valid PointScaleCreateCommand pointScaleCreateCommand) {
        // TODO: implement this
        return null;
    }
}
