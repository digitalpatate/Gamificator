package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.BadgeCreateCommand;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.pointScale.PointScaleController;
import ch.heigvd.amt.gamificator.api.pointScale.PointScaleService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BadgeControllerTest {

    final long AUTH_APP_ID = 1;

    @MockBean
    BadgeService badgeService;

    @MockBean
    SecurityContextService securityContextService;

    @InjectMocks
    BadgeController badgeController;

    @Autowired
    ApplicationContext context;

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
        badgeCreateCommand.setName("Batman");
        badgeCreateCommand.setImageUrl(new URI("https://external-content.duckduckgo.com/iu/" +
                "?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5PHxSPT-OxxSWoYBFuD-GAHaLV%26pid%3DApi&f=1"));

        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setName("The name");
        badgeDTO.setImageUrl(new URI("https://external-content.duckduckgo.com/iu/" +
                "?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5PHxSPT-OxxSWoYBFuD-GAHaLV%26pid%3DApi&f=1"));


        when(badgeService.registerNewBadge(any(BadgeCreateCommand.class), any(Long.class))).thenReturn(badgeDTO);

        ResponseEntity responseEntity = badgeController.createBadge(badgeCreateCommand);
        BadgeDTO badgeDTOGot = (BadgeDTO) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(badgeDTO.getName(), badgeDTOGot.getName());
        assertEquals(badgeDTO.getImageUrl(), badgeDTOGot.getImageUrl());
    }

}
