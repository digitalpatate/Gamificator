package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
            throw new NotFoundException("Not found");
        }

        PointScale pointScale = PointScale.toEntity(pointScaleCreateCommand);
        Application application = getApplicationById(pointScaleCreateCommand.getApplicationId());
        pointScale.setApplication(application);
        pointScale.setId(id);

        PointScale updatedPointScale = pointScaleRepository.save(pointScale);

        return PointScale.toDTO(updatedPointScale);
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

        pointScales = pointScaleRepository.findByApplicationId(applicationId);

        List<PointScaleDTO> pointScaleDTOs = new LinkedList<>();

        for(PointScale pointScale : pointScales){
            pointScaleDTOs.add(PointScale.toDTO(pointScale));
        }

        return pointScaleDTOs;
    }

    public void deletePointScaleById(Long id) {
        try {
            pointScaleRepository.deleteById(id);
        } catch(EmptyResultDataAccessException ignored) {
            // Do not leak what point scales exists or not
        }
    }

    public PointScaleDTO getPointScaleById(Long id) throws NotFoundException {
        PointScale pointScale = pointScaleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found"));

        return PointScale.toDTO(pointScale);
    }

    private Application getApplicationById(Long applicationId) throws NotFoundException {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Not found"));
    }
}
