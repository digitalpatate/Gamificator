package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    private final ApplicationRepository applicationRepository;

    public BadgeDTO registerNewBadge(BadgeCreateCommand badge, Long applicationId) throws AlreadyExistException, RelatedObjectNotFound {
        Badge newBadge = BadgeMapper.toEntity(badge);

        Optional<Badge> b = badgeRepository.findByNameAndApplicationId(newBadge.getName(), applicationId);
        if (b.isPresent()){
            throw new AlreadyExistException("Badge already exist");
        }

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new RelatedObjectNotFound("Application not found"));
        newBadge.setApplication(application);
        newBadge = badgeRepository.save(newBadge);

        return BadgeMapper.toDTO(newBadge);
    }

    public List<BadgeDTO> getAllBadges(Long applicationId) {
        Iterable<Badge> badges = badgeRepository.findByApplicationId(applicationId);
        List<BadgeDTO> badgesDTO = new LinkedList<>();

        for(Badge badge : badges){
            badgesDTO.add(BadgeMapper.toDTO(badge));
        }

        return badgesDTO;
    }

    public BadgeDTO getById(Long id, Long applicationId) throws NotFoundException {
        Badge badge = badgeRepository.findByIdAndApplicationId(id, applicationId).orElseThrow(() -> new NotFoundException("Not found"));

        return BadgeMapper.toDTO(badge);
    }

    public BadgeDTO updateById(Long id, BadgeDTO badgeDTO, Long applicationId) throws RelatedObjectNotFound, NotFoundException {
        Badge badge = BadgeMapper.toEntity(badgeDTO);
        badge.setId(id);

        Optional<Badge> b = badgeRepository.findByIdAndApplicationId(id, applicationId);
        if (b.isEmpty()){
            throw new NotFoundException("Badge not found");
        }

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new RelatedObjectNotFound("Application not found"));
        badge.setApplication(application);

        badgeRepository.save(badge);

        return BadgeMapper.toDTO(badge);
    }
}
