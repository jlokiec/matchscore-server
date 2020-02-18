package pl.matchscore.server.test.services;

import org.junit.jupiter.api.BeforeEach;
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
import pl.matchscore.server.dao.TeamDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.Team;
import pl.matchscore.server.models.dto.TeamDto;
import pl.matchscore.server.services.TeamService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeamServiceTest {
    @Autowired
    private TeamDao teamDao;

    @Autowired
    private LeagueDao leagueDao;

    private TeamService service;

    @BeforeEach
    public void init() {
        service = new TeamService(teamDao);
    }

    @Test
    public void testGetAll() {
        initTeams();

        List<TeamDto> teams = service.getAll();
        assertNotNull(teams);
        assertEquals(4, teams.size());
    }

    @Test
    public void testGetAll_Empty() {
        List<TeamDto> teams = service.getAll();
        assertNotNull(teams);
        assertTrue(teams.isEmpty());
    }

    private void initTeams() {
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
        teamDao.save(team1);

        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Team 2");
        team2.setLeague(league1);
        teamDao.save(team2);

        Team team3 = new Team();
        team3.setId(3);
        team3.setName("Team 3");
        team3.setLeague(league2);
        teamDao.save(team3);

        Team team4 = new Team();
        team4.setId(4);
        team4.setName("Team 4");
        team4.setLeague(league2);
        teamDao.save(team4);
    }
}
