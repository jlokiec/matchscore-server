package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matchscore.server.models.Player;

import java.util.List;

@Repository
public interface PlayerDao extends JpaRepository<Player, Long> {
    Player findById(long id);

    List<Player> findByTeam_Id(long teamId);
}
