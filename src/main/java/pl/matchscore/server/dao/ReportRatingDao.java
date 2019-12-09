package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.ReportRating;

@Repository
public interface ReportRatingDao extends JpaRepository<ReportRating, Long> {
}
