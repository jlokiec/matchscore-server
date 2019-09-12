package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private LeagueCategory category;

    public League() {
    }

    public League(long id, String name, LeagueCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
