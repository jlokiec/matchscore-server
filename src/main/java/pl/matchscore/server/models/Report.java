package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private Long startTimestamp;

    private Long endTimestamp;

    @ManyToOne
    private Match match;

    @OneToOne(mappedBy = "report")
    private ReportRating reportRating;

    @OneToMany(mappedBy = "report")
    private List<Event> events;

    public Report() {
        events = new ArrayList<>();
    }
}
