package pl.matchscore.server.models.dto;

import lombok.Data;

@Data
public class RateReportDto {
    private long reportId;
    private int rating;
    private long ratingTimestamp;
}
