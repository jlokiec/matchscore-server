package pl.matchscore.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matchscore.server.models.Match;

import java.util.List;

public interface MatchDao extends JpaRepository<Match, Long> {
    List<Match> findByLeague_Id(long leagueId);

    List<Match> findByKickOffTimestampBetween(long start, long end);

    List<Match> findByLeague_IdAndKickOffTimestampBetween(long leagueId, long start, long end);
}
