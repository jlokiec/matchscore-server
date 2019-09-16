package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "league")
    private List<Team> teams;

    public League() {
        teams = new ArrayList<>();
    }

    public League(long id, String name, LeagueCategory category) {
        this(id, name, category, new ArrayList<>());
    }

    public League(long id, String name, LeagueCategory category, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.teams = teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }
}
