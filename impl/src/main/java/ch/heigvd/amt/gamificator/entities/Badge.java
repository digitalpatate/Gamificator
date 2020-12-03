package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne
    private Application application;

    private String name;
    private String imageUrl;
}
