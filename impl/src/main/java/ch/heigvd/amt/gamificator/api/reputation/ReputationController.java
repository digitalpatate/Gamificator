package ch.heigvd.amt.gamificator.api.reputation;

import ch.heigvd.amt.gamificator.api.BadgesApi;
import ch.heigvd.amt.gamificator.api.ReputationsApi;
import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.model.ReputationDTO;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.ApiException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Log
public class ReputationController implements ReputationsApi {
    @Autowired
    private ReputationService reputationService;

    @Autowired
    private SecurityContextService securityContextService;

    @Override
    public ResponseEntity<ReputationDTO> getReputationByUserUUID(UUID uuid) {
        ReputationDTO reputationDTO = null;

        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            reputationDTO = reputationService.getReputationByUserId(uuid);
        } catch(NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(reputationDTO, HttpStatus.OK);
    }
}
