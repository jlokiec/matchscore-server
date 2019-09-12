package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private League league;

    public Team() {
    }

    public Team(long id, String name, League league) {
        this.id = id;
        this.name = name;
        this.league = league;
    }
}
