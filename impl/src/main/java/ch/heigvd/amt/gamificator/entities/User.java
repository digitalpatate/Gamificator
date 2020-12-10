package ch.heigvd.amt.gamificator.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    //@Generated(GenerationTime.NEVER)
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id", length=36)
    public String UUID;
}
