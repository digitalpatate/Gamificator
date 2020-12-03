import ch.heigvd.amt.gamificator.api.badge.BadgeMapper;
import ch.heigvd.amt.gamificator.api.badge.BadgeService;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.Badge;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BadgeServiceTest {
    static BadgeService badgeService;
    @MockBean
    static BadgeRepository badgeRepository;
    @MockBean
    static ApplicationRepository applicationRepository;

    @BeforeEach
    public void setUp(){
        this.badgeService = new BadgeService(badgeRepository, applicationRepository);
    }

    /*@Test
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
        BadgeDTO newBadge = new BadgeDTO();

        newBadge.setName("demon lord");
        try {
            newBadge.setImageUrl(new URI("www.google.com/Pikachu.png"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ch.heigvd.amt.gamificator.entities.Badge badge = new ch.heigvd.amt.gamificator.entities.Badge();

        badge.setName("demon lord");
        badge.setImageUrl("www.google.com/Pikachu.png");
        badge.setId(1);

        when(badgeRepository.save(any())).thenReturn(badge);

        Badge createdBadge = null;
        try {
            createdBadge = badgeService.registerNewBadge(newBadge, 5l);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(badge.getId(), (int)createdBadge.getId());
    }*/
}
