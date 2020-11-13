package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ch.heigvd.amt.gamificator.entities.Application.toEntity;

@Service
@AllArgsConstructor
public class PointScaleService {

    private final PointScaleRepository pointScaleRepository;
    private final ApplicationRepository applicationRepository;

    public PointScaleDTO createPointScale(PointScaleCreateCommand pointScaleCreateCommand) throws NotFoundException {
        PointScale pointScale = PointScale.toEntity(pointScaleCreateCommand);
        Application application = getApplicationById(pointScaleCreateCommand.getApplicationId());
        pointScale.setApplication(application);

        PointScale pointScaleCreated = pointScaleRepository.save(pointScale);
        PointScaleDTO pointScaleDTO = PointScale.toDTO(pointScaleCreated);

        return pointScaleDTO;
    }

    public PointScaleDTO updatePointScale(Long id, PointScaleCreateCommand pointScaleCreateCommand) throws NotFoundException {
        if(!pointScaleRepository.existsById(id)) {
            throw new NotFoundException(404,"Not found");
        }

        PointScale pointScale = PointScale.toEntity(pointScaleCreateCommand);
        Application application = getApplicationById(pointScaleCreateCommand.getApplicationId());
        pointScale.setApplication(application);
        pointScale.setId(id);
        pointScaleRepository.save(pointScale);

        return PointScale.toDTO(pointScale);
    }

    public List<PointScaleDTO> getAllPointScales() {
        Iterable<PointScale> pointScales = new ArrayList<>();
        pointScales = pointScaleRepository.findAll();

        List<PointScaleDTO> pointScaleDTOs = new LinkedList<>();

        for(PointScale pointScale : pointScales){
            pointScaleDTOs.add(PointScale.toDTO(pointScale));
        }

        return pointScaleDTOs;
    }

    public List<PointScaleDTO> getAllPointScaleOfApplication(Long applicationId) throws NotFoundException {
        Iterable<PointScale> pointScales = new ArrayList<>();

        Application application = getApplicationById(applicationId);
        pointScales = pointScaleRepository.findByApplication(application);

        List<PointScaleDTO> pointScaleDTOs = new LinkedList<>();

        for(PointScale pointScale : pointScales){
            pointScaleDTOs.add(PointScale.toDTO(pointScale));
        }

        return pointScaleDTOs;
    }

    public void deletePointScaleById(Long id) throws NotFoundException {
        try {
            pointScaleRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ignored) {

        }
    }

    public PointScaleDTO getPointScaleById(Long id) throws NotFoundException {
        PointScale pointScale = pointScaleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(404,"Not found"));

        return PointScale.toDTO(pointScale);
    }

    private Application getApplicationById(Long applicationId) throws NotFoundException {
        // Retrieve the application with this id.
        // It will throws NotFoundException it does not exists
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException(404, "Not found"));
    }
}
