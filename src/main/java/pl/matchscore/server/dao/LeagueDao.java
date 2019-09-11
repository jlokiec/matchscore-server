package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matchscore.server.models.League;

import java.util.List;

public interface LeagueDao extends JpaRepository<League, Long> {
    List<League> findAllByCategory_Id(long categoryId);
}
