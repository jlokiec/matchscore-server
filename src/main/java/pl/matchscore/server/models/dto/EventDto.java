package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Event;
import pl.matchscore.server.models.EventCategory;
import pl.matchscore.server.models.EventType;

@Data
public class EventDto {
    private long id;
    private long timestamp;
    private EventType eventType;
    private long reportId;
    private String description;
    private EventCategory category;

    public EventDto(Event event) {
        this.id = event.getId();
        this.timestamp = event.getTimestamp();
        this.eventType = event.getEventType();
        this.reportId = event.getReport().getId();
        this.description = event.getDescription();
        this.category = event.getCategory();
    }
}
