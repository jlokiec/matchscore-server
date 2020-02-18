package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.League;

@Data
public class LeagueDto {
    private long id;
    private long categoryId;
    private String name;

    public LeagueDto() {
    }

    public LeagueDto(League league) {
        this.id = league.getId();
        this.categoryId = league.getCategory().getId();
        this.name = league.getName();
    }
}
