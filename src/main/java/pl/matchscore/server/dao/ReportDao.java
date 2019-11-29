package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.Report;

import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    Report findById(long id);

    List<Report> findByMatch_Id(long matchId);
}
