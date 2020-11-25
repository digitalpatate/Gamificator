package application;

import ch.heigvd.amt.gamificator.api.application.ApplicationMapper;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;

import static ch.heigvd.amt.gamificator.api.application.ApplicationMapper.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationMapperTest {

    ApplicationService applicationService;

    @MockBean
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationContext context;

   /* @BeforeEach
    public void init(){
        this.applicationRepository = mock(ApplicationRepository.class);
        this.applicationService = new ApplicationService(applicationRepository);
    }

    @Test
    public void toEntityShouldReturnAWellFormedEntity() throws URISyntaxException {
        ApplicationCreateCommand command = new ApplicationCreateCommand();

        command.setName("test-app");
        command.setUrl(new URI("amt.jackeri.ch"));

        Application application = toEntity(command);

        assertEquals(application.getName(),"test-app");
        assertEquals(application.getUrl(),"amt.jackeri.ch");

        assertNotNull(application.getKey());
        assertNotNull(application.getSecret());
    }*/
    @Test
    public void toEntityFromApplicationShouldReturnAWellFormedDTO(){
        Application application = createApplication();
        ApplicationDTO applicationDTO = ApplicationMapper.toDTO(application);

        assertEquals((long)applicationDTO.getId(),(long) application.getId());
        assertEquals(applicationDTO.getName(),application.getName());
        assertEquals(applicationDTO.getUrl().toString(),application.getUrl());
    }
    @Test
    public void toEntityFromCreateCommandShouldReturnAWellFormedEntity(){
        ApplicationCreateCommand command = new ApplicationCreateCommand();

        command.setName("name");
        try {
            command.setUrl(new URI("http://lcoalhost:8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Application application = toEntity(command);

        assertEquals(application.getName(),command.getName());
        assertEquals(application.getUrl().toString(),command.getUrl().toString());
        assertNotNull(application.getSecret());
        assertNotNull(application.getKey());
    }


    private Application createApplication(){
        Application application = new Application();
        application.setId(1);
        application.setSecret("secret");
        application.setName("name");
        application.setUrl("http://localhost");
        application.setKey("key");
        return application;

    }

}
