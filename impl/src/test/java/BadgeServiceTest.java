import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.badge.BadgeService;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationRegistrationDTO;
import ch.heigvd.amt.gamificator.api.model.Badge;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BadgeServiceTest {
    static BadgeService badgeService;
    static BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp(){
        this.badgeRepository = mock(BadgeRepository.class);
        this.badgeService = new BadgeService(badgeRepository);
    }

    @Test
    public void toEntityShouldReturnAWellFormedEntity() {
        Badge newBadge = new Badge();

        newBadge.setName("demon lord");
        newBadge.setApplicationId(666);
        newBadge.setImage(null);

        ch.heigvd.amt.gamificator.entities.Badge badge = this.badgeService.toEntity(newBadge);

        assertEquals(badge.getName(),"demon lord");
        assertEquals(badge.getApplicationId(), 666);
        assertEquals(badge.getImage(), null);
        assertNotNull(badge.getId());
    }

    @Test
    public void createShouldReturnAnId() {
        Badge newBadge = new Badge();

        newBadge.setName("demon lord");
        newBadge.setApplicationId(666);
        newBadge.setImage(null);

        ch.heigvd.amt.gamificator.entities.Badge badge = new ch.heigvd.amt.gamificator.entities.Badge();

        badge.setName("demon lord");
        badge.setApplicationId(666);
        badge.setImage(null);
        badge.setId(1);

        when(badgeRepository.save(any())).thenReturn(badge);

        ch.heigvd.amt.gamificator.entities.Badge createdBadge = badgeService.registerNewBadge(newBadge);

        assertEquals(badge.getId(),(int)createdBadge.getId());

    }
}
