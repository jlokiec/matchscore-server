package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private int number;

    @ManyToOne
    private Team team;
}
