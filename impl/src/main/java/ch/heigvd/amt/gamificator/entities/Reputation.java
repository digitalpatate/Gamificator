package ch.heigvd.amt.gamificator.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Reputation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Reward> reward = new LinkedList<>();

    public void addAll(List<Reward> rewards) {
        this.reward.addAll(rewards);
    }
}
