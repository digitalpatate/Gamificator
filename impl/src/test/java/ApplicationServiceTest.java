import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreate;
import ch.heigvd.amt.gamificator.api.model.ApplicationRegistrationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ApplicationServiceTest {

    ApplicationService applicationService;

    ApplicationRepository applicationRepository;
    @BeforeClass
    public void setUp(){
        this.applicationRepository = mock(ApplicationRepository.class);
        this.applicationService = new ApplicationService(applicationRepository);
    }

    @Test
    public void toEntityShouldReturnAWellFormedEntity() throws URISyntaxException {
        ApplicationCreate applicationCreate = new ApplicationCreate();

        applicationCreate.setName("test-app");
        applicationCreate.setUrl(new URI("amt.jackeri.ch"));

        Application application = this.applicationService.toEntity(applicationCreate);

        assertEquals(application.getName(),"test-app");
        assertEquals(application.getUrl(),"amt.jackeri.ch");

        assertNotNull(application.getKey());
        assertNotNull(application.getSecret());
    }

    @Test
    public void createShouldReturnAnId() throws URISyntaxException {
        ApplicationCreate applicationCreate = new ApplicationCreate();

        applicationCreate.setName("test-app");
        applicationCreate.setUrl(new URI("amt.jackeri.ch"));

        Application application = new Application();

        application.setName("test-app");
        application.setUrl("amt.jackeri.ch");
        application.setSecret("secret");
        application.setKey("key");
        application.setId(10);

        when(applicationRepository.save(application)).thenReturn(application);

        ApplicationRegistrationDTO applicationRegistrationDTO = applicationService.registerNewApplication(applicationCreate);

        assertEquals(application.getId(),(int)applicationRegistrationDTO.getId());

    }
}
