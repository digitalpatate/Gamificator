package ch.heigvd.amt.gamificator.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Data
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    public Rule rule;
}
