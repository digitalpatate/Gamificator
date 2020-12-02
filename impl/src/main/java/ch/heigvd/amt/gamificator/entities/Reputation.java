package ch.heigvd.amt.gamificator.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reputation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    User user;

    @OneToOne
    Rule rule;
}
