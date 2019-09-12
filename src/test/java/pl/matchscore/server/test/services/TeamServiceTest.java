package pl.matchscore.server.test.services;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.matchscore.server.dao.TeamDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.Team;
import pl.matchscore.server.models.dto.TeamDto;
import pl.matchscore.server.services.TeamService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamServiceTest {
    private TeamDao dao;
    private TeamService service;

    @BeforeEach
    public void init() {
        dao = mock(TeamDao.class);
        service = new TeamService(dao);
    }

    @AfterEach
    public void clean() {
        dao = null;
        service = null;
    }

    @Test
    public void testGetAll() {
        when(dao.findAll()).thenReturn(initTeams());

        List<TeamDto> teams = service.getAll();

        assertEquals(2, teams.size(), "teams size is incorrect");
        assertEquals(1, teams.get(0).getId(), "team id is incorrect");
        assertEquals("Widzew Łódź", teams.get(0).getName(), "team name is incorrect");
        assertEquals(1, teams.get(0).getLeagueId(), "team league id is incorrect");
        assertEquals(2, teams.get(1).getId(), "team id is incorrect");
        assertEquals("Górnik Łęczna", teams.get(1).getName(), "team name is incorrect");
        assertEquals(1, teams.get(1).getLeagueId(), "team league id is incorrect");
    }

    @Test
    public void testGetAll_NoTeams() {
        when(dao.findAll()).thenReturn(new ArrayList<>());

        List<TeamDto> teams = service.getAll();

        assertTrue(teams.isEmpty(), "teams size is incorrect");
    }

    private List<Team> initTeams() {
        LeagueCategory category = new LeagueCategory(1, "ligi centralne");
        League secondDivision = new League(1, "II liga", category);
        Team team1 = new Team(1, "Widzew Łódź", secondDivision);
        Team team2 = new Team(2, "Górnik Łęczna", secondDivision);

        return Lists.newArrayList(team1, team2);
    }
}
