package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.Event;

import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
    List<Event> findByReport_Id(long reportId);
}
