package PointScale;

import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.api.pointScale.PointScaleController;
import ch.heigvd.amt.gamificator.api.pointScale.PointScaleService;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointScaleControllerTest {

    @MockBean
    PointScaleService pointScaleService;

    @InjectMocks
    PointScaleController pointScaleController;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void init(){
        this.pointScaleService = mock(PointScaleService.class);
        this.pointScaleController = new PointScaleController();
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    public void creatingAPointScaleShouldReturnAResponseWithTheDTOAndSuccess() {
        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("The name");
        pointScaleCreateCommand.setDescription("The description");
        pointScaleCreateCommand.setApplicationId(1L);

        PointScaleDTO pointScaleDTO = new PointScaleDTO();
        pointScaleDTO.setName("The name");
        pointScaleDTO.setDescription("The description");
        pointScaleDTO.setApplicationId(1L);

        when(pointScaleService.createPointScale(any(PointScaleCreateCommand.class))).thenReturn(pointScaleDTO);

        ResponseEntity responseEntity = pointScaleController.createPointScale(pointScaleCreateCommand);
        PointScaleDTO pointScaleDTOGot = (PointScaleDTO) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(pointScaleDTO.getName(), pointScaleDTOGot.getName());
        assertEquals(pointScaleDTO.getDescription(), pointScaleDTOGot.getDescription());
        assertEquals(pointScaleDTO.getApplicationId(), pointScaleDTOGot.getApplicationId());
    }

    @SneakyThrows
    @Test
    public void creatingAPointScaleWithAnApplicatioIdThatDoesNotExistsShouldReturnAnErrorCode() {
        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("The name");
        pointScaleCreateCommand.setDescription("The description");
        pointScaleCreateCommand.setApplicationId(1L);

        when(pointScaleService.createPointScale(any(PointScaleCreateCommand.class)))
                .thenThrow(new NotFoundException(400, "Not found"));

        ResponseEntity responseEntity = pointScaleController.createPointScale(pointScaleCreateCommand);
        PointScaleDTO pointScaleDTOGot = (PointScaleDTO) responseEntity.getBody();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void deletingAPointScaleShouldReturnNoContent() {
        ResponseEntity responseEntity = pointScaleController.deletePointScale(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void gettingAllPointScaleWithAnApplicationIdThatDoesNotExistsShouldRespondNotFound() {
        when(pointScaleService.getAllPointScaleOfApplication(1L))
                .thenThrow(new NotFoundException(400, "Not found"));

        ResponseEntity responseEntity = pointScaleController.getAllPointScales(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void gettingAllPointScaleWithAnApplicationIdShouldReturnThosePointScales() {
        PointScaleDTO pointScaleDTO1 = new PointScaleDTO();
        pointScaleDTO1.setId(1L);
        pointScaleDTO1.setApplicationId(1L);
        pointScaleDTO1.setName("Point Scale 1");
        pointScaleDTO1.setDescription("Description of Point Scale 1");

        PointScaleDTO pointScaleDTO2 = new PointScaleDTO();
        pointScaleDTO2.setId(2L);
        pointScaleDTO2.setApplicationId(1L);
        pointScaleDTO2.setName("Point Scale 2");
        pointScaleDTO2.setDescription("Description of Point Scale 2");

        List<PointScaleDTO> pointScaleDTOS = new ArrayList<>();
        pointScaleDTOS.add(pointScaleDTO1);
        pointScaleDTOS.add(pointScaleDTO2);

        when(pointScaleService.getAllPointScaleOfApplication(1L)).thenReturn(pointScaleDTOS);

        ResponseEntity responseEntity = pointScaleController.getAllPointScales(1L);
        List<PointScaleDTO> pointScaleDTOSGot = (List<PointScaleDTO>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pointScaleDTOS.size(), pointScaleDTOSGot.size());
        assertNotNull(pointScaleDTOSGot.get(0));
        assertNotNull(pointScaleDTOSGot.get(1));
    }

    @SneakyThrows
    @Test
    public void gettingAPointScaleShouldRespondItsDTO() {
        PointScaleDTO pointScaleDTO = new PointScaleDTO();
        pointScaleDTO.setId(1L);
        pointScaleDTO.setName("Craziness");
        pointScaleDTO.setDescription("Are you THAT crazy ?!");
        pointScaleDTO.setApplicationId(1L);

        when(pointScaleService.getPointScaleById(1L)).thenReturn(pointScaleDTO);

        ResponseEntity responseEntity = pointScaleController.getPointScale(1L);
        PointScaleDTO pointScaleDTOGot = (PointScaleDTO) responseEntity.getBody();

        assertNotNull(pointScaleDTOGot);
        assertEquals(pointScaleDTO.getId(), pointScaleDTOGot.getId());
        assertEquals(pointScaleDTO.getName(), pointScaleDTOGot.getName());
        assertEquals(pointScaleDTO.getDescription(), pointScaleDTOGot.getDescription());
        assertEquals(pointScaleDTO.getApplicationId(), pointScaleDTOGot.getApplicationId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void gettingAPointScaleThatDoesNotExistsShouldRespondNotFound() {
        PointScaleDTO pointScaleDTO = new PointScaleDTO();
        pointScaleDTO.setId(1L);
        pointScaleDTO.setName("Craziness");
        pointScaleDTO.setDescription("Are you THAT crazy ?!");
        pointScaleDTO.setApplicationId(1L);

        when(pointScaleService.getPointScaleById(1L))
                .thenThrow(new NotFoundException(400, "Not found"));

        ResponseEntity responseEntity = pointScaleController.getPointScale(1L);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void updatePointScaleShouldRespondTheUpdatedPointScaleDTO() {
        PointScaleDTO newPointScaleDTO = new PointScaleDTO();
        newPointScaleDTO.setId(1L);
        newPointScaleDTO.setName("Craziness");
        newPointScaleDTO.setDescription("Are you THAT crazy ?!");
        newPointScaleDTO.setApplicationId(1L);

        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("Craziness");
        pointScaleCreateCommand.setDescription("Are you THAT crazy ?!");
        pointScaleCreateCommand.setApplicationId(1L);

        when(pointScaleService.updatePointScale(1L, pointScaleCreateCommand))
                .thenReturn(newPointScaleDTO);

        ResponseEntity responseEntity = pointScaleController.updatePointScale(1L, pointScaleCreateCommand);
        PointScaleDTO pointScaleDTOGot = (PointScaleDTO) responseEntity.getBody();

        assertNotNull(pointScaleDTOGot);
        assertEquals(newPointScaleDTO.getId(), pointScaleDTOGot.getId());
        assertEquals(newPointScaleDTO.getName(), pointScaleDTOGot.getName());
        assertEquals(newPointScaleDTO.getDescription(), pointScaleDTOGot.getDescription());
        assertEquals(newPointScaleDTO.getApplicationId(), pointScaleDTOGot.getApplicationId());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @SneakyThrows
    @Test
    public void updatingAPointScaleThatDoesNotExistsShouldRespondNotFound() {
        PointScaleDTO newPointScaleDTO = new PointScaleDTO();
        newPointScaleDTO.setId(1L);
        newPointScaleDTO.setName("Craziness");
        newPointScaleDTO.setDescription("Are you THAT crazy ?!");
        newPointScaleDTO.setApplicationId(1L);

        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName("Craziness");
        pointScaleCreateCommand.setDescription("Are you THAT crazy ?!");
        pointScaleCreateCommand.setApplicationId(1L);

        when(pointScaleService.updatePointScale(1L, pointScaleCreateCommand))
                .thenThrow(new NotFoundException(400, "Not found"));

        ResponseEntity responseEntity = pointScaleController.updatePointScale(1L, pointScaleCreateCommand);

        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
