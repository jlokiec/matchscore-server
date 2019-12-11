package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.Report;

import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    Report findById(long id);

    Report findByMatch_IdAndUser_Username(long id, String username);

    List<Report> findByEndTimestampIsNotNullAndReportRating_RatingIsNull();

    List<Report> findByMatch_IdAndStartTimestampIsNotNull(long matchId);
}
