package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private League league;

    private int round;

    private long kickOffTimestamp;

    private Integer homeGoals;

    @ManyToOne
    private Team homeTeam;

    private Integer awayGoals;

    @ManyToOne
    private Team awayTeam;

    private boolean live;
}
