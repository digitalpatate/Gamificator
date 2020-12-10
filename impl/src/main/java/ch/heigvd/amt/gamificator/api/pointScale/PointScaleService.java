package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.application.ApplicationMapper;
import ch.heigvd.amt.gamificator.api.application.ApplicationService;
import ch.heigvd.amt.gamificator.api.model.ApplicationDTO;
import ch.heigvd.amt.gamificator.api.model.LeaderBoardDTO;
import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.exceptions.AlreadyExistException;
import ch.heigvd.amt.gamificator.exceptions.NotAuthorizedException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class PointScaleService {

    private final PointScaleRepository pointScaleRepository;

    @Autowired
    private ApplicationService applicationService;

    public PointScaleDTO createPointScale(PointScaleCreateCommand pointScaleCreateCommand, long applicationId) throws NotFoundException, AlreadyExistException {
        PointScale pointScale = PointScaleMapper.toEntity(pointScaleCreateCommand);

        ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
        Application application = ApplicationMapper.toEntity(applicationDTO);

        if(pointScaleRepository.findByNameAndApplicationId(pointScaleCreateCommand.getName(), applicationId).isPresent()) {
            throw new AlreadyExistException("Point scale already exists with this name!");
        }

        pointScale.setApplication(application);

        PointScale pointScaleCreated = pointScaleRepository.save(pointScale);
        PointScaleDTO pointScaleDTO = PointScaleMapper.toDTO(pointScaleCreated);

        return pointScaleDTO;
    }

    public PointScaleDTO updatePointScale(Long id, PointScaleCreateCommand pointScaleCreateCommand, long applicationId) throws NotFoundException {
        if(!pointScaleRepository.existsById(id)) {
            throw new NotFoundException("Not found");
        }

        PointScale pointScale = PointScaleMapper.toEntity(pointScaleCreateCommand);
        ApplicationDTO applicationDTO = applicationService.getApplicationById(applicationId);
        Application application = ApplicationMapper.toEntity(applicationDTO);

        pointScale.setApplication(application);
        pointScale.setId(id);

        PointScale updatedPointScale = pointScaleRepository.save(pointScale);

        return PointScaleMapper.toDTO(updatedPointScale);
    }

    public List<PointScaleDTO> getAllPointScaleOfApplication(Long applicationId) throws NotFoundException {
        Iterable<PointScale> pointScales = new ArrayList<>();

        pointScales = pointScaleRepository.findByApplicationId(applicationId);

        List<PointScaleDTO> pointScaleDTOs = new LinkedList<>();

        for(PointScale pointScale : pointScales){
            pointScaleDTOs.add(PointScaleMapper.toDTO(pointScale));
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

        return PointScaleMapper.toDTO(pointScale);
    }

    public boolean isPointScaleFromThisApplication(long pointScaleId, long applicationId) throws NotFoundException {
        PointScale pointScale = pointScaleRepository.findById(pointScaleId)
                .orElseThrow(() -> new NotFoundException("Not found"));

        return pointScale.getApplication().getId() == applicationId;
    }
}
