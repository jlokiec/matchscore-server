package pl.matchscore.server.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.matchscore.server.models.League;

@Data
public class LeagueDto {
    private long id;

    @JsonProperty("category_id")
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
