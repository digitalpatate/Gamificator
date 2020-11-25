import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
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

public class ApplicationServiceTest {

    ApplicationService applicationService;

    @MockBean
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
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
    }

}
