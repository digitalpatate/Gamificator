package ch.heigvd.amt.gamificator.api.pointScale;
import ch.heigvd.amt.gamificator.api.PointScalesApi;
import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PointScaleController implements PointScalesApi {

    @Autowired
    private PointScaleService pointScaleService;

    @Autowired
    private SecurityContextService securityContextService;

    @Override
    public ResponseEntity<PointScaleDTO> createPointScale(@Valid @RequestBody PointScaleCreateCommand pointScaleCreateCommand) {
        PointScaleDTO pointScaleDTO = null;

        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            pointScaleDTO = pointScaleService.createPointScale(pointScaleCreateCommand, applicationId);
        } catch (NotFoundException | AlreadyExistException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PointScaleDTO>> getAllPointScales() {
        List<PointScaleDTO> pointScaleDTOs = null;

        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            pointScaleDTOs = pointScaleService.getAllPointScaleOfApplication(applicationId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(pointScaleDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PointScaleDTO> getPointScale(@PathVariable Long id) {
        PointScaleDTO pointScaleDTO = null;

        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            if (!pointScaleService.isPointScaleFromThisApplication(id, applicationId)) {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        try {
            pointScaleDTO = pointScaleService.getPointScaleById(id);
        } catch(NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PointScaleDTO> updatePointScale(@PathVariable Long id, @Valid PointScaleCreateCommand pointScaleCreateCommand) {
        PointScaleDTO pointScaleDTO = null;

        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            if (!pointScaleService.isPointScaleFromThisApplication(id, applicationId)) {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        try {
            pointScaleDTO = pointScaleService.updatePointScale(id, pointScaleCreateCommand, applicationId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(pointScaleDTO, HttpStatus.OK);
    }
}
