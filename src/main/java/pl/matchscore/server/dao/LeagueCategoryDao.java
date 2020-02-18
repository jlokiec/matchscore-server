package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.LeagueCategory;

@Repository
public interface LeagueCategoryDao extends JpaRepository<LeagueCategory, Long> {

}
