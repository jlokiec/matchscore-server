package pl.matchscore.server.test.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.matchscore.server.dao.LeagueDao;
import pl.matchscore.server.dao.MatchDao;
import pl.matchscore.server.dao.TeamDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.Match;
import pl.matchscore.server.models.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MatchDaoTest {
    @Autowired
    private MatchDao matchDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private LeagueDao leagueDao;

    @BeforeAll
    public void init() {
        League league1 = new League();
        league1.setId(1);
        league1.setName("League 1");
        league1 = leagueDao.save(league1);

        League league2 = new League();
        league2.setId(2);
        league2.setName("League 2");
        league2 = leagueDao.save(league2);

        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Team 1");
        team1.setLeague(league1);
        team1 = teamDao.save(team1);

        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Team 2");
        team2.setLeague(league1);
        team2 = teamDao.save(team2);

        Team team3 = new Team();
        team3.setId(3);
        team3.setName("Team 3");
        team3.setLeague(league2);
        team3 = teamDao.save(team3);

        Team team4 = new Team();
        team4.setId(4);
        team4.setName("Team 4");
        team4.setLeague(league2);
        team4 = teamDao.save(team4);

        Match match1 = new Match();
        match1.setId(1);
        match1.setLeague(league1);
        match1.setHomeTeam(team1);
        match1.setAwayTeam(team2);
        match1.setKickOffTimestamp(1569088800);
        matchDao.save(match1);

        Match match2 = new Match();
        match2.setId(2);
        match2.setLeague(league2);
        match2.setHomeTeam(team3);
        match2.setAwayTeam(team4);
        match2.setKickOffTimestamp(1569088800);
        matchDao.save(match2);

        Match match3 = new Match();
        match3.setId(3);
        match3.setLeague(league2);
        match3.setHomeTeam(team3);
        match3.setAwayTeam(team4);
        match3.setKickOffTimestamp(1569175200);
        matchDao.save(match3);
    }

    @Test
    public void testFindByLeagueId() {
        List<Match> matches = matchDao.findByLeague_Id(1);
        assertNotNull(matches);
        assertEquals(1, matches.size());
    }

    @Test
    public void testFindByLeagueId_InvalidLeagueId() {
        List<Match> matches = matchDao.findByLeague_Id(3);
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindByKickOffTimestampBetween() {
        List<Match> matches = matchDao.findByKickOffTimestampBetween(1569024000, 1569110399);
        assertNotNull(matches);
        assertEquals(2, matches.size());
    }

    @Test
    public void testFindByKickOffTimestampBetween_InvalidTimestamp() {
        List<Match> matches = matchDao.findByKickOffTimestampBetween(1568937600, 1569023999);
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindByLeagueIdAndKickOffTimestampBetween() {
        List<Match> matches = matchDao.findByLeague_IdAndKickOffTimestampBetween(1, 1569024000, 1569110399);
        assertNotNull(matches);
        assertEquals(1, matches.size());
    }

    @Test
    public void testFindByLeagueIdAndKickOffTimestampBetween_InvalidLeagueId() {
        List<Match> matches = matchDao.findByLeague_IdAndKickOffTimestampBetween(3, 1569024000, 1569110399);
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindByLeagueIdAndKickOffTimestampBetween_InvalidTimestamp() {
        List<Match> matches = matchDao.findByLeague_IdAndKickOffTimestampBetween(1, 1568937600, 1569023999);
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }
}
