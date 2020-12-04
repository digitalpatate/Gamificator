package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Badge;

import java.net.URI;
import java.net.URISyntaxException;

public class BadgeMapper {

    public static Badge toEntity(BadgeDTO newBadge) {
        Badge badge = new Badge();
        badge.setName(newBadge.getName());
        badge.setImageUrl(newBadge.getImageUrl().toString());

        return badge;
    }

    public static Badge toEntity(BadgeCreateCommand newBadge) {
        Badge badge = new Badge();
        badge.setName(newBadge.getName());
        badge.setImageUrl(newBadge.getImageUrl().toString());

        return badge;
    }

    public static BadgeDTO toDTO(Badge badge) {
        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setId(badge.getId());
        badgeDTO.setName(badge.getName());
        try {
            badgeDTO.setImageUrl(new URI(badge.getImageUrl()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return badgeDTO;
    }
}
