package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.ReportRating;

@Data
public class ReportRatingDto {
    private long id;
    private long reportId;
    private int rating;
    private String ratedBy;
    private long ratingTimestamp;
    private String comment;

    public ReportRatingDto(ReportRating reportRating) {
        this.id = reportRating.getId();
        this.reportId = reportRating.getReport().getId();
        this.rating = reportRating.getRating();
        this.ratedBy = reportRating.getRatedBy().getUsername();
        this.ratingTimestamp = reportRating.getRatingTimestamp();
        this.comment = reportRating.getComment();
    }
}
