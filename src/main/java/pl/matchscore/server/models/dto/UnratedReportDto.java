package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Report;

@Data
public class UnratedReportDto {
    private long id;
    private String username;
    private String description;

    public UnratedReportDto(Report report) {
        this.id = report.getId();
        this.username = report.getUser().getUsername();
        this.description = getDescription(report);
    }

    private String getDescription(Report report) {
        return report.getMatch().getHomeTeam().getName() + " - " + report.getMatch().getAwayTeam().getName();
    }
}
