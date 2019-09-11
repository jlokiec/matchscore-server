package pl.matchscore.server.test.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.matchscore.server.dao.LeagueDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.dto.LeagueDto;
import pl.matchscore.server.services.LeagueService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeagueServiceTest {
    private LeagueService service;
    private LeagueDao dao;

    @BeforeEach
    public void init() {
        dao = mock(LeagueDao.class);
        service = new LeagueService(dao);
    }

    @AfterEach
    public void clean() {
        dao = null;
        service = null;
    }

    @Test
    public void testGetAll() {
        when(dao.findAll()).thenReturn(initLeagues());

        List<LeagueDto> leagues = service.getAll();

        assertEquals(5, leagues.size(), "leagues size is incorrect");
        assertEquals(1, leagues.get(0).getId(), "league id is incorect");
        assertEquals(1, leagues.get(0).getCategoryId(), "league category id is incorrect");
        assertEquals("Ekstraklasa", leagues.get(0).getName(), "league name is incorrect");
        assertEquals(2, leagues.get(1).getId(), "league id is incorect");
        assertEquals(1, leagues.get(1).getCategoryId(), "league category id is incorrect");
        assertEquals("I liga", leagues.get(1).getName(), "league name is incorrect");
        assertEquals(3, leagues.get(2).getId(), "league id is incorect");
        assertEquals(1, leagues.get(2).getCategoryId(), "league category id is incorrect");
        assertEquals("II liga", leagues.get(2).getName(), "league name is incorrect");
        assertEquals(4, leagues.get(3).getId(), "league id is incorect");
        assertEquals(2, leagues.get(3).getCategoryId(), "league category id is incorrect");
        assertEquals("III liga grupa I", leagues.get(3).getName(), "league name is incorrect");
        assertEquals(5, leagues.get(4).getId(), "league id is incorect");
        assertEquals(3, leagues.get(4).getCategoryId(), "league category id is incorrect");
        assertEquals("IV liga", leagues.get(4).getName(), "league name is incorrect");
    }

    @Test
    public void testGetAll_NoLeagues() {
        when(dao.findAll()).thenReturn(new ArrayList<>());

        List<LeagueDto> leagues = service.getAll();

        assertTrue(leagues.isEmpty(), "leagues size is incorrect");
    }

    @Test
    public void testGetByCategoryId() {
        long categoryId = 1;
        when(dao.findAllByCategory_Id(categoryId)).thenReturn(initLeaguesByCategory());
        when(dao.findAll()).thenReturn(initLeagues());

        List<LeagueDto> leagues = service.getByCategoryId(categoryId);

        assertEquals(3, leagues.size(), "leagues by category size is incorrect");
        assertEquals(1, leagues.get(0).getId(), "league id is incorect");
        assertEquals(1, leagues.get(0).getCategoryId(), "league category id is incorrect");
        assertEquals("Ekstraklasa", leagues.get(0).getName(), "league name is incorrect");
        assertEquals(2, leagues.get(1).getId(), "league id is incorect");
        assertEquals(1, leagues.get(1).getCategoryId(), "league category id is incorrect");
        assertEquals("I liga", leagues.get(1).getName(), "league name is incorrect");
        assertEquals(3, leagues.get(2).getId(), "league id is incorect");
        assertEquals(1, leagues.get(2).getCategoryId(), "league category id is incorrect");
        assertEquals("II liga", leagues.get(2).getName(), "league name is incorrect");
    }

    @Test
    public void testGetByCategoryId_InvalidCategoryId() {
        long categoryId = 100;
        when(dao.findAllByCategory_Id(categoryId)).thenReturn(new ArrayList<>());

        List<LeagueDto> leagues = service.getByCategoryId(categoryId);

        assertTrue(leagues.isEmpty(), "leagues by category size is incorrect");
    }

    private List<League> initLeagues() {
        List<League> leagues = new ArrayList<>();

        LeagueCategory topLeagues = new LeagueCategory(1, "Ligi centralne");
        LeagueCategory thirdLeagues = new LeagueCategory(2, "III ligi");
        LeagueCategory localLeagues = new LeagueCategory(3, "Województwo łódzkie");

        leagues.add(new League(1, "Ekstraklasa", topLeagues));
        leagues.add(new League(2, "I liga", topLeagues));
        leagues.add(new League(3, "II liga", topLeagues));
        leagues.add(new League(4, "III liga grupa I", thirdLeagues));
        leagues.add(new League(5, "IV liga", localLeagues));

        return leagues;
    }

    private List<League> initLeaguesByCategory() {
        return initLeagues()
                .stream()
                .filter(league -> league.getCategory().getId() == 1)
                .collect(Collectors.toList());
    }
}
