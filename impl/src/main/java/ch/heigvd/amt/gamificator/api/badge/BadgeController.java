package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.BadgesApi;
import ch.heigvd.amt.gamificator.api.model.Badge;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class BadgeController implements BadgesApi {
    @Autowired
    private BadgeService badgeService;

    @Override
    public ResponseEntity<Void> createBadge(@ApiParam(value = "") @RequestPart(value="name", required=false)  String name, @ApiParam(value = "") @RequestPart(value="applicationId", required=false)  Integer applicationId, @ApiParam(value = "") @Valid @RequestPart(value = "image") MultipartFile image) {
        ch.heigvd.amt.gamificator.entities.Badge badgeRegistrationDTO = badgeService.registerNewBadge(name, applicationId, image);

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
