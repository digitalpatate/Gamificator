package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.BadgesApi;
import ch.heigvd.amt.gamificator.api.model.ApplicationRead;
import ch.heigvd.amt.gamificator.api.model.Badge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
public class BadgeController implements BadgesApi {
    @Autowired
    private BadgeService badgeService;

    @Override
    public ResponseEntity<Void> createBadge(@Valid @RequestBody Badge badge) {

        ch.heigvd.amt.gamificator.entities.Badge badgeRegistrationDTO = badgeService.registerNewBadge(badge);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/badges/{id}")
                .buildAndExpand(badgeRegistrationDTO.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteBadge(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Badge>> getAllbadges() {
        return null;
    }

    @Override
    public ResponseEntity<Badge> getBadge(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateBadge(Integer id, @Valid Badge badge) {
        return null;
    }
}
