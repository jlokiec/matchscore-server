package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long timestamp;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @ManyToOne
    private Report report;

    private String description;
}
