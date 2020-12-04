package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityContextService {

    @Autowired
    private ApplicationService applicationService;

    public long getApplicationIdFromAuthentifiedApp() {
        String[] principals = (String[]) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        long applicationId = 0;

        try {
            applicationId = applicationService.getApplicationIdFromApiKey(principals[0]);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return applicationId;
    }
}
