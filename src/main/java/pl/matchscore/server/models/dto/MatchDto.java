package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Match;

@Data
public class MatchDto {
    private long id;
    private int round;
    private long kickOffTimestamp;
    private Integer homeGoals;
    private TeamDto homeTeam;
    private Integer awayGoals;
    private TeamDto awayTeam;
    private boolean live;

    public MatchDto() {
    }

    public MatchDto(Match match) {
        this.id = match.getId();
        this.round = match.getRound();
        this.kickOffTimestamp = match.getKickOffTimestamp();
        this.homeGoals = match.getHomeGoals();
        this.homeTeam = new TeamDto(match.getHomeTeam());
        this.awayGoals = match.getAwayGoals();
        this.awayTeam = new TeamDto(match.getAwayTeam());
        this.live = match.isLive();
    }
}
