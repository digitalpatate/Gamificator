package ch.heigvd.amt.gamificator.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@DiscriminatorValue("badge")
public class BadgeReward extends Reward{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Badge badge;
}
