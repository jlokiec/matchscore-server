package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    public Team() {
        players = new ArrayList<>();
    }

    public Team(long id, String name, League league) {
        this.id = id;
        this.name = name;
        this.league = league;
        players = new ArrayList<>();
    }
}
