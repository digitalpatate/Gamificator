package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.application.ApplicationMapper;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.api.pointScale.PointScaleMapper;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    private final ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

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

    public BadgeDTO updateBadge(Long id, BadgeCreateCommand badgeCreateCommand, long applicationId) throws NotFoundException {
        if(!badgeRepository.existsById(id)) {
            throw new NotFoundException("Not found");
        }

        Badge badge = BadgeMapper.toEntity(badgeCreateCommand);
        ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
        Application application = ApplicationMapper.toEntity(applicationDTO);

        badge.setApplication(application);
        badge.setId(id);

        Badge updatedbadge =  badgeRepository.save(badge);

        return BadgeMapper.toDTO(updatedbadge);
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

    public boolean isBadgeFromThisApplication(long badgeId, long applicationId) throws NotFoundException {
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new NotFoundException("Not found"));

        return badge.getApplication().getId() == applicationId;
    }
}
