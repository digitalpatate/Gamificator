package ch.heigvd.amt.gamificator.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    public String UUID;
}
