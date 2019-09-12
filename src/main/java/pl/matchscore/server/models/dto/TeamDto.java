package pl.matchscore.server.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.matchscore.server.models.Team;

@Data
public class TeamDto {
    private long id;

    @JsonProperty("league_id")
    private long leagueId;

    private String name;

    public TeamDto() {
    }

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.leagueId = team.getLeague().getId();
    }
}