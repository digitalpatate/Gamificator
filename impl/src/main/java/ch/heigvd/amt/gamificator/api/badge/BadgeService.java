package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    private final ApplicationRepository applicationRepository;

    public BadgeDTO registerNewBadge(BadgeDTO badge, Long applicationId) throws NotFoundException {
        Badge newBadge = BadgeMapper.toEntity(badge);
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Not found"));
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

    public BadgeDTO updateById(Long id, BadgeDTO badgeDTO, Long applicationId) throws NotFoundException {
        Badge badge = BadgeMapper.toEntity(badgeDTO);
        badge.setId(id);
        badge.setName(badgeDTO.getName());
        badge.setImageUrl(badgeDTO.getImageUrl().toString());

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Not found"));
        badge.setApplication(application);

        badgeRepository.save(badge);

        return BadgeMapper.toDTO(badge);
    }
}
