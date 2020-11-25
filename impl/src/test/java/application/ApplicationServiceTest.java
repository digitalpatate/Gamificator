package application;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.mock;

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


}
