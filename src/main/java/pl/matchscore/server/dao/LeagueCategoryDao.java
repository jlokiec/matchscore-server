package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matchscore.server.models.LeagueCategory;

public interface LeagueCategoryDao extends JpaRepository<LeagueCategory,Long> {

}
