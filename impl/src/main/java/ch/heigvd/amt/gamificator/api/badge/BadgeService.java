package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.Badge;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public ch.heigvd.amt.gamificator.entities.Badge registerNewBadge(Badge badge) {
        ch.heigvd.amt.gamificator.entities.Badge newBadge = toEntity(badge);

        newBadge = badgeRepository.save(newBadge);

        return newBadge;
    }

    public ch.heigvd.amt.gamificator.entities.Badge toEntity(Badge newBadge) {
        ch.heigvd.amt.gamificator.entities.Badge badge = new ch.heigvd.amt.gamificator.entities.Badge();

        badge.setName(newBadge.getName());
        badge.setApplicationId(newBadge.getApplicationId());
        try {
            badge.setImage(newBadge.getImage().getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return badge;
    }
}
