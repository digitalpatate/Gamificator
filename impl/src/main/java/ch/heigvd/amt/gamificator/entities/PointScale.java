package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.PointScaleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.PointScaleDTO;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointScale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Application application;

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

        if(pointScale.getApplication() != null) {
            pointScaleDTO.setApplicationId(pointScale.getApplication().getId());
        }

        return pointScaleDTO;
    }

}
