package ch.heigvd.amt.gamificator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BadgeReward extends Reward{
    @OneToOne
    private Badge badge;
}
