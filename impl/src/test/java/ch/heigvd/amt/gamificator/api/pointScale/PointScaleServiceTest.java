package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PointScaleServiceTest {

    PointScaleService pointScaleService;

    @InjectMocks
    ApplicationService applicationService;

    @MockBean
    PointScaleRepository pointScaleRepository;

    @MockBean
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void init(){
        this.applicationRepository = mock(ApplicationRepository.class);
        this.pointScaleRepository = mock(PointScaleRepository.class);
        this.applicationService = new ApplicationService(applicationRepository);
        MockitoAnnotations.initMocks(this);

        this.pointScaleService = new PointScaleService(pointScaleRepository, applicationService);
    }

    @Test
    public void toEntityShouldReturnAWellFormedEntity() throws URISyntaxException {
        String name = "CommunityScore";
        String description = "Rewards users help to the community";
        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName(name);
        pointScaleCreateCommand.setDescription(description);

        PointScale pointScale = PointScaleMapper.toEntity(pointScaleCreateCommand);
        assertEquals(name, pointScale.getName());
        assertEquals(description, pointScale.getDescription());
    }

    @Test
    public void creatingAPointScaleShouldReturnItsDTO() {
        String name = "CommunityScore";
        String description = "Rewards users help to the community";
        long applicationId = 1;
        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName(name);
        pointScaleCreateCommand.setDescription(description);

        Application application = new Application();
        application.setId(applicationId);
        application.setUrl("http://localhost:6969/");

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(pointScaleRepository.save(any(PointScale.class))).then(returnsFirstArg());

        PointScaleDTO createdPointScaleDTO = null;

        try {
            createdPointScaleDTO = pointScaleService.createPointScale(pointScaleCreateCommand, applicationId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(name, createdPointScaleDTO.getName());
        assertEquals(description, createdPointScaleDTO.getDescription());
    }

    @Test
    public void updatePointScaleShouldReturnsTheUpdatedPointScaleDTO() {
        long applicationId = 1;
        Application application = new Application();
        application.setId(applicationId);
        application.setUrl("http://localhost:6969/");

        when(pointScaleRepository.save(any(PointScale.class))).then(returnsFirstArg());
        when(pointScaleRepository.existsById(1L)).thenReturn(true);
        when(applicationRepository.findById(any(Long.class))).thenReturn(Optional.of(application));

        PointScaleCreateCommand oldPointScaleCreateCommand = new PointScaleCreateCommand();
        oldPointScaleCreateCommand.setName("not this name...");
        oldPointScaleCreateCommand.setDescription("not this description...");

        try {
            pointScaleService.createPointScale(oldPointScaleCreateCommand, applicationId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        long newId = 1;
        String newName = "CommunityScore";
        String newDescription = "Rewards users help to the community";
        long newApplicationId = 1;
        PointScaleCreateCommand pointScaleCreateCommand = new PointScaleCreateCommand();
        pointScaleCreateCommand.setName(newName);
        pointScaleCreateCommand.setDescription(newDescription);

        PointScaleDTO updatedPointScaleDTO = null;

        try {
            updatedPointScaleDTO = pointScaleService.updatePointScale(newId, pointScaleCreateCommand, applicationId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(newName, updatedPointScaleDTO.getName());
        assertEquals(newDescription, updatedPointScaleDTO.getDescription());
    }

    @Test
    public void getAllShouldReturnOnlyPointScaleWithTheSpecifiedApplicationId() {

        Application application1 = new Application();
        application1.setId(1);

        PointScale pointScale1 = new PointScale();
        pointScale1.setId(1);
        pointScale1.setApplication(application1);
        pointScale1.setName("Point Scale 1");
        pointScale1.setDescription("Description of Point Scale 1");

        Application application2 = new Application();
        application2.setId(2);

        PointScale pointScale2 = new PointScale();
        pointScale2.setId(2);
        pointScale2.setApplication(application2);
        pointScale2.setName("Point Scale 2");
        pointScale2.setDescription("Description of Point Scale 2");

        PointScale pointScale3 = new PointScale();
        pointScale3.setId(3);
        pointScale3.setApplication(application2);
        pointScale3.setName("Point Scale 3");
        pointScale3.setDescription("Description of Point Scale 3");

        List<PointScale> pointScalesWithApplication1 = new ArrayList<>();
        pointScalesWithApplication1.add(pointScale1);

        List<PointScale> pointScalesWithApplication2 = new ArrayList<>();
        pointScalesWithApplication2.add(pointScale2);
        pointScalesWithApplication2.add(pointScale3);

        List<PointScale> pointScalesWithApplication3 = new ArrayList<>();

        when(pointScaleRepository.findByApplicationId(1L)).thenReturn(pointScalesWithApplication1);
        when(pointScaleRepository.findByApplicationId(2L)).thenReturn(pointScalesWithApplication2);
        when(pointScaleRepository.findByApplicationId(3L)).thenReturn(pointScalesWithApplication3);

        List<PointScaleDTO> pointScalesDTOsOfApplication1Got = null;

        try {
            pointScalesDTOsOfApplication1Got = pointScaleService.getAllPointScaleOfApplication(1L);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        List<PointScaleDTO> pointScalesDTOsOfApplication2Got = null;

        try {
            pointScalesDTOsOfApplication2Got = pointScaleService.getAllPointScaleOfApplication(2L);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        List<PointScaleDTO> pointScalesDTOsOfApplication3Got = null;

        try {
            pointScalesDTOsOfApplication3Got = pointScaleService.getAllPointScaleOfApplication(3L);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


        assertEquals(1, pointScalesDTOsOfApplication1Got.size());
        assertNotNull(pointScalesDTOsOfApplication1Got.get(0));
        assertEquals(2, pointScalesDTOsOfApplication2Got.size());
        assertNotNull(pointScalesDTOsOfApplication2Got.get(0));
        assertNotNull(pointScalesDTOsOfApplication2Got.get(1));
        assertEquals(0, pointScalesDTOsOfApplication3Got.size());
    }

    @Test
    public void findByIdShouldReturnsThePointScaleWithThisId() {
        Application application = new Application();
        application.setId(1L);

        PointScale pointScale = new PointScale();
        pointScale.setId(1);
        pointScale.setName("Awesomeness");
        pointScale.setDescription("Awesoooooooome !!!!");
        pointScale.setApplication(application);

        when(pointScaleRepository.findById(any(Long.class))).thenReturn(Optional.of(pointScale));

        PointScaleDTO pointScaleDTO = null;
        try {
            pointScaleDTO = pointScaleService.getPointScaleById(1L);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(1L, pointScaleDTO.getId());
        assertEquals("Awesomeness", pointScaleDTO.getName());
        assertEquals("Awesoooooooome !!!!", pointScaleDTO.getDescription());
    }

    @Test
    public void findByIdShouldThrowExceptionIfThePointScaleDoesntExists() {
        when(pointScaleRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pointScaleService.getPointScaleById(1L));
    }

    @Test
    public void deletingAPointScaleThatExistsShouldNotThrows() {
        doNothing().when(pointScaleRepository).deleteById(1L);
        assertDoesNotThrow(() -> pointScaleService.deletePointScaleById(1L));
    }
}
