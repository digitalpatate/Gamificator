package ch.heigvd.amt.gamificator.api.pointScale;

import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.entities.PointScale;

public class PointScaleMapper {

    public static PointScale toEntity(PointScaleCreateCommand pointScaleCreateCommand) {
        PointScale pointScale = new PointScale();
        pointScale.setName(pointScaleCreateCommand.getName());
        pointScale.setDescription(pointScaleCreateCommand.getDescription().toString());

        return pointScale;
    }

    public static PointScaleDTO toDTO(PointScale pointScale) {
        PointScaleDTO pointScaleDTO =  new PointScaleDTO();
        pointScaleDTO.setId(pointScale.getId());
        pointScaleDTO.setName(pointScale.getName());
        pointScaleDTO.setDescription(pointScale.getDescription().toString());

        /*if(pointScale.getApplication() != null) {
            pointScaleDTO.setApplicationId(pointScale.getApplication().getId());
        }*/

        return pointScaleDTO;
    }
}
