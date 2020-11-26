package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public Badge registerNewBadge(BadgeDTO badge) {
        Badge newBadge = toEntity(badge);
        newBadge = badgeRepository.save(newBadge);

        return newBadge;
    }

    public Badge toEntity(BadgeDTO newBadge) {
        Badge badge = new Badge();
        badge.setName(newBadge.getName());
        badge.setImageUrl(newBadge.getImageUrl().toString());

        return badge;
    }

    public List<BadgeDTO> getAllBadges() {
        Iterable<Badge> badges = badgeRepository.findAll();
        List<BadgeDTO> badgesDTO = new LinkedList<>();

        for(Badge badge : badges){
            badgesDTO.add(Badge.toDTO(badge));
        }

        return badgesDTO;
    }

    public BadgeDTO getById(Long id) throws NotFoundException {
        Badge badge = badgeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));

        return Badge.toDTO(badge);
    }

    public BadgeDTO updateById(long id, BadgeDTO badgeDTO) {
        Badge badge = toEntity(badgeDTO);
        badge.setId(id);
        badge.setName(badgeDTO.getName());
        badge.setImageUrl(badgeDTO.getImageUrl().toString());

        badgeRepository.save(badge);

        return Badge.toDTO(badge);
    }
}
