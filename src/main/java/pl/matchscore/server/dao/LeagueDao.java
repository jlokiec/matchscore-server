package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.League;

import java.util.List;

@Repository
public interface LeagueDao extends JpaRepository<League, Long> {
    List<League> findAllByCategory_Id(long categoryId);
}
