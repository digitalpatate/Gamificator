package ch.heigvd.amt.gamificator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class UserId implements Serializable {
    @Column(length=36)
    private String UUID;
    private long applicationId;
}
