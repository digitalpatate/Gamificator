package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class PointScaleService {

    private final PointScaleRepository pointScaleRepository;

    public PointScale toEntity(PointScaleCreateCommand pointScaleCreateCommand) {
        PointScale pointScale =  new PointScale();
        pointScale.setName(pointScaleCreateCommand.getName());
        pointScale.setDescription(pointScaleCreateCommand.getDescription().toString());

        return pointScale;
    }

    public PointScaleDTO toDTO(PointScale pointScale) {
        PointScaleDTO pointScaleDTO =  new PointScaleDTO();
        pointScaleDTO.setId(pointScale.getId());
        pointScaleDTO.setName(pointScale.getName());
        pointScaleDTO.setDescription(pointScale.getDescription().toString());

        if(pointScale.getApplication() != null) {
            pointScaleDTO.setApplicationId(pointScale.getApplication().getId());
        }

        return pointScaleDTO;
    }

    public PointScaleDTO createPointScale(PointScaleCreateCommand pointScaleCreateCommand) {
        PointScale pointScale = toEntity(pointScaleCreateCommand);
        PointScale pointScaleCreated = pointScaleRepository.save(pointScale);
        PointScaleDTO pointScaleDTO = toDTO(pointScaleCreated);

        return pointScaleDTO;
    }

    public List<PointScaleDTO> getAllPointScales() {
        Iterable<PointScale> pointScales = new ArrayList<>();
        pointScales = pointScaleRepository.findAll();

        List<PointScaleDTO> pointScaleDTOs = new LinkedList<>();

        for(PointScale pointScale : pointScales){
            pointScaleDTOs.add(toDTO(pointScale));
        }

        return pointScaleDTOs;
    }

    public void deletePointScaleById(Long id) {
        pointScaleRepository.deleteById(id);
    }
}
