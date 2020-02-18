package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "league_categories")
public class LeagueCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<League> leagues;

    public LeagueCategory() {
        leagues = new ArrayList<>();
    }

    public LeagueCategory(long id, String name) {
        this.id = id;
        this.name = name;
        leagues = new ArrayList<>();
    }

    public LeagueCategory(long id, String name, List<League> leagues) {
        this.id = id;
        this.name = name;
        this.leagues = leagues;
    }

    public void addLeague(League league) {
        leagues.add(league);
    }

    public void removeLeague(League league) {
        leagues.remove(league);
    }
}
