package ch.heigvd.amt.gamificator.api.application;

import ch.heigvd.amt.gamificator.api.model.ApplicationCreateCommand;
import ch.heigvd.amt.gamificator.api.model.ApplicationCreateDTO;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @SneakyThrows
    @Test
    public void createShouldReturnADTOFromCommand(){
        ApplicationCreateCommand command = new ApplicationCreateCommand();
        command.setName("name");
        command.setUrl(new URI("http://lcoalhost:8080"));

        ApplicationCreateDTO dto = new ApplicationCreateDTO();
        dto.setId(0);
        dto.setKey("key");
        dto.setSecret("secret");

        when(applicationRepository.save(any(Application.class))).then(returnsFirstArg());


        ApplicationCreateDTO resultDto = applicationService.create(command);

        //Could not mock static mehtod to check key and secret value
        assertEquals(resultDto.getId(),dto.getId());
        assertNotNull(resultDto.getKey());
        assertNotNull(resultDto.getSecret());
    }

    @SneakyThrows
    @Test
    public void updateByIdShouldReturnAnUpdateAppDTO(){
        ApplicationCreateCommand command = new ApplicationCreateCommand();
        command.setName("newName");
        command.setUrl(new URI("http://newurl:8080"));

        when(applicationRepository.save(any(Application.class))).then(returnsFirstArg());

        ApplicationDTO resultDto = applicationService.updateById((long)0,command);

        assertEquals(resultDto.getUrl(),command.getUrl());
        assertEquals(resultDto.getName(),command.getName());
        assertEquals(resultDto.getId(),0);
    }
}
