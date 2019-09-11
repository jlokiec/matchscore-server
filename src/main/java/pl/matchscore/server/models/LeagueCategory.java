package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "league_categories")
public class LeagueCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public LeagueCategory() {
    }

    public LeagueCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
