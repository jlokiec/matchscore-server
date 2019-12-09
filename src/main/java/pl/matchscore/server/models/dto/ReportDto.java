package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Report;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReportDto {
    private long id;
    private Long startTimestamp;
    private Long endTimestamp;
    private MatchDto match;
    private List<EventDto> events;
    private String username;
    private ReportRatingDto rating;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.startTimestamp = report.getStartTimestamp();
        this.endTimestamp = report.getEndTimestamp();
        this.match = new MatchDto(report.getMatch());
        this.events = report.getEvents().stream()
                .map(event -> new EventDto(event))
                .collect(Collectors.toList());
        this.username = report.getUser().getUsername();
        this.rating = report.getReportRating() != null ? new ReportRatingDto(report.getReportRating()) : null;
    }
}
