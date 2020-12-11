package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BadgeControllerTest {

    final long AUTH_APP_ID = 1;
    final String NAME = "Batman";
    final URI IMG_URL = new URI("https://external-content.duckduckgo.com/iu/" +
            "?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5PHxSPT-OxxSWoYBFuD-GAHaLV%26pid%3DApi&f=1");

    @MockBean
    BadgeService badgeService;

    @MockBean
    SecurityContextService securityContextService;

    @InjectMocks
    BadgeController badgeController;

    @Autowired
    ApplicationContext context;

    public BadgeControllerTest() throws URISyntaxException {
    }

    @BeforeEach
    public void init(){
        this.badgeService = mock(BadgeService.class);
        this.securityContextService = mock(SecurityContextService.class);
        this.badgeController = new BadgeController();

        MockitoAnnotations.initMocks(this);
        when(securityContextService.getApplicationIdFromAuthentifiedApp()).thenReturn(AUTH_APP_ID);
    }

    @SneakyThrows
    @Test
    public void creatingABadgeShouldReturnAResponseWithTheDTOAndSuccess() {
        BadgeCreateCommand badgeCreateCommand = new BadgeCreateCommand();
        badgeCreateCommand.setName(this.NAME);
        badgeCreateCommand.setImageUrl(this.IMG_URL);

        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setName(this.NAME);
        badgeDTO.setImageUrl(this.IMG_URL);


        when(badgeService.registerNewBadge(any(BadgeCreateCommand.class), any(Long.class))).thenReturn(badgeDTO);

        ResponseEntity responseEntity = badgeController.createBadge(badgeCreateCommand);
        BadgeDTO badgeDTOGot = (BadgeDTO) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(badgeDTO.getName(), badgeDTOGot.getName());
        assertEquals(badgeDTO.getImageUrl(), badgeDTOGot.getImageUrl());
    }

    @SneakyThrows
    @Test
    public void gettingAllBadgeShouldReturnAllThebadges() {
        BadgeDTO badgeDTO1 = new BadgeDTO();
        badgeDTO1.setId(1L);
        badgeDTO1.setName(this.NAME);
        badgeDTO1.setImageUrl(this.IMG_URL);

        BadgeDTO badgeDTO2 = new BadgeDTO();
        badgeDTO2.setId(2L);
        badgeDTO2.setName(this.NAME + "2");
        badgeDTO2.setImageUrl(this.IMG_URL);

        List<BadgeDTO> badgeDTOs = new ArrayList<>();
        badgeDTOs.add(badgeDTO1);
        badgeDTOs.add(badgeDTO2);

        when(badgeService.getAllBadges(any(Long.class))).thenReturn(badgeDTOs);

        ResponseEntity responseEntity = badgeController.getAllbadges();
        List<BadgeDTO> badgeDTOsGot = (List<BadgeDTO>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(badgeDTOs.size(), badgeDTOsGot.size());
        assertNotNull(badgeDTOsGot.get(0));
        assertNotNull(badgeDTOsGot.get(1));
    }

    @SneakyThrows
    @Test
    public void gettingABadgeShouldRespondItsDTO() {
        BadgeDTO BadgeDTO = new BadgeDTO();
        BadgeDTO.setId(1L);
        BadgeDTO.setName(this.NAME);
        BadgeDTO.setImageUrl(this.IMG_URL);


        when(badgeService.getById(any(Long.class), any(Long.class))).thenReturn(BadgeDTO);

        ResponseEntity responseEntity = badgeController.getBadge(1L);
        BadgeDTO BadgeDTOGot = (BadgeDTO) responseEntity.getBody();

        assertNotNull(BadgeDTOGot);
        assertEquals(BadgeDTO.getId(), BadgeDTOGot.getId());
        assertEquals(BadgeDTO.getName(), BadgeDTOGot.getName());
        assertEquals(BadgeDTO.getImageUrl(), BadgeDTOGot.getImageUrl());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
