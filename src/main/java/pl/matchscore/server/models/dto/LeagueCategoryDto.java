package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.LeagueCategory;

@Data
public class LeagueCategoryDto {
    private long id;
    private String name;

    public LeagueCategoryDto() {
    }

    public LeagueCategoryDto(LeagueCategory leagueCategory) {
        this.id = leagueCategory.getId();
        this.name = leagueCategory.getName();
    }
}
