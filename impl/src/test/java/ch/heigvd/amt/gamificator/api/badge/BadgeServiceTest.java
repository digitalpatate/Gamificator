package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BadgeServiceTest {
    static BadgeService badgeService;
    @InjectMocks
    ApplicationService applicationService;
    @MockBean
    static BadgeRepository badgeRepository;
    @MockBean
    static ApplicationRepository applicationRepository;

    @BeforeEach
    public void setUp(){
        badgeRepository = mock(BadgeRepository.class);
        applicationRepository = mock(ApplicationRepository.class);

        this.badgeService = new BadgeService(badgeRepository, applicationRepository, applicationService);
    }

    @Test
    public void toEntityShouldReturnAWellFormedEntity() {
        BadgeDTO newBadge = new BadgeDTO();

        newBadge.setName("demon lord");
        try {
            newBadge.setImageUrl(new URI("www.google.com/Pikachu.png"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Badge badge = BadgeMapper.toEntity(newBadge);

        assertEquals(badge.getName(),"demon lord");
        assertEquals(badge.getImageUrl(), "www.google.com/Pikachu.png");
        assertNotNull(badge.getId());
    }

    @Test
    public void createShouldReturnAnId() {
        BadgeCreateCommand newBadge = new BadgeCreateCommand();

        newBadge.setName("demon lord");
        try {
            newBadge.setImageUrl(new URI("www.google.com/Pikachu.png"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Badge badge = new Badge();

        badge.setName("demon lord");
        badge.setImageUrl("www.google.com/Pikachu.png");
        badge.setId(1);

        Application application = new Application();
        application.setId(5);

        when(applicationRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(application));
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        BadgeDTO createdBadge = null;
        try {
            createdBadge = badgeService.registerNewBadge(newBadge, 5l);
        } catch (AlreadyExistException | RelatedObjectNotFound e) {
            e.printStackTrace();
        }

        assertEquals(badge.getId(), createdBadge.getId().longValue());
    }
}
