package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Report;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReportDto {
    private long id;
    private long startTimestamp;
    private long endTimestamp;
    private long matchId;
    private List<EventDto> events;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.startTimestamp = report.getStartTimestamp();
        this.endTimestamp = report.getEndTimestamp();
        this.matchId = report.getMatch().getId();
        this.events = report.getEvents().stream()
                .map(event -> new EventDto(event))
                .collect(Collectors.toList());
    }
}
