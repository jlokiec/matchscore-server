package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matchscore.server.models.Team;

public interface TeamDao extends JpaRepository<Team, Long> {

}
