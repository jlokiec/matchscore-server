package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.EventCategory;
import pl.matchscore.server.models.EventType;

@Data
public class CreateEventDto {
    private long timestamp;
    private EventType eventType;
    private long reportId;
    private String description;
    private EventCategory category;
}
