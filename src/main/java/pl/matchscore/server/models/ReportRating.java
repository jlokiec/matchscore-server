package pl.matchscore.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "report_ratings")
public class ReportRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Report report;

    private int rating;

    @ManyToOne
    private User ratedBy;

    private long ratingTimestamp;
}
