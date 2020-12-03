package ch.heigvd.amt.gamificator.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PointsReward extends Reward {
    @OneToOne
    private PointScale pointScale;

    private int points;
}