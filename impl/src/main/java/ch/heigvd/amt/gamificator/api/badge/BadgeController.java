package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.BadgesApi;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.exceptions.ApiException;
import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class BadgeController implements BadgesApi {
    @Autowired
    private BadgeService badgeService;

    @Autowired
    private SecurityContextService securityContextService;

    @Override
    public ResponseEntity<BadgeDTO> createBadge(BadgeCreateCommand badgeCreateCommand) {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        BadgeDTO badgeRegistrationDTO = null;
        try {
            badgeRegistrationDTO = badgeService.registerNewBadge(badgeCreateCommand, applicationId);
        } catch (AlreadyExistException | RelatedObjectNotFound e) {
            return new ResponseEntity(e.getMessage(), e.getCode());
        }

        return new ResponseEntity(badgeRegistrationDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<BadgeDTO>> getAllbadges() {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        List<BadgeDTO> badges = badgeService.getAllBadges(applicationId);

        return new ResponseEntity(badges, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BadgeDTO> getBadge(Long id) {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        try {
            BadgeDTO badgeDTO = badgeService.getById(id, applicationId);
            return new ResponseEntity(badgeDTO, HttpStatus.OK);
        } catch (ApiException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<BadgeDTO> updateBadge(Long id, BadgeDTO badge) {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        BadgeDTO badgeDTO = null;
        try {
            badgeDTO = badgeService.updateById(id, badge, applicationId);
        } catch (RelatedObjectNotFound | NotFoundException e) {
            return new ResponseEntity(e.getMessage(), e.getCode());
        }

        return new ResponseEntity(badgeDTO, HttpStatus.OK);
    }
}
