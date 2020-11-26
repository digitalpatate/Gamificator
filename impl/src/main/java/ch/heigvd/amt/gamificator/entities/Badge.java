package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URI;
import java.net.URISyntaxException;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String imageUrl;

    public static BadgeDTO toDTO(Badge badge) {
        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setName(badge.getName());
        try {
            badgeDTO.setImageUrl(new URI(badge.getImageUrl()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return badgeDTO;
    }
}
