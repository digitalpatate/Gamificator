package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.BadgesApi;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.ApiException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class BadgeController implements BadgesApi {
    @Autowired
    private BadgeService badgeService;

    @Override
    public ResponseEntity<Void> createBadge(BadgeDTO badgeDTO) {
        long applicationId = (long) SecurityContextHolder.getContext().getAuthentication().getDetails();
        log.info(String.valueOf(applicationId));
        Badge badgeRegistrationDTO = badgeService.registerNewBadge(badgeDTO);

        return new ResponseEntity(badgeRegistrationDTO, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<BadgeDTO>> getAllbadges() {
        List<BadgeDTO> badges = badgeService.getAllBadges();

        return new ResponseEntity(badges, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BadgeDTO> getBadge(Integer id) {
        try {
            BadgeDTO badgeDTO = badgeService.getById(id.longValue());
            return new ResponseEntity(badgeDTO, HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> updateBadge(Integer id, BadgeDTO badge) {
        BadgeDTO badgeDTO = badgeService.updateById(id.longValue(), badge);

        return new ResponseEntity(badgeDTO, HttpStatus.OK);
    }
}
