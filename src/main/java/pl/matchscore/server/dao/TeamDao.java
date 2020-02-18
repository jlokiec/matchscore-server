package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.Team;

@Repository
public interface TeamDao extends JpaRepository<Team, Long> {

}
