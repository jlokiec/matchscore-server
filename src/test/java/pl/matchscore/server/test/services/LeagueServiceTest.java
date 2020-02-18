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
import pl.matchscore.server.dao.LeagueCategoryDao;
import pl.matchscore.server.dao.LeagueDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.dto.LeagueDto;
import pl.matchscore.server.services.LeagueService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeagueServiceTest {
    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private LeagueCategoryDao leagueCategoryDao;

    private LeagueService service;

    @BeforeEach
    public void init() {
        service = new LeagueService(leagueDao);
    }

    @Test
    public void testGetAll() {
        initLeagues();

        List<LeagueDto> leagues = service.getAll();
        assertNotNull(leagues);
        assertEquals(3, leagues.size());
    }

    @Test
    public void testGetAll_Empty() {
        List<LeagueDto> leagues = service.getAll();
        assertNotNull(leagues);
        assertTrue(leagues.isEmpty());
    }

    @Test
    public void testGetByCategoryId() {
        initLeagues();

        List<LeagueDto> leagues = service.getByCategoryId(1);
        assertNotNull(leagues);
        assertEquals(2, leagues.size());
    }

    @Test
    public void testGetByCategoryId_Empty() {
        List<LeagueDto> leagues = service.getByCategoryId(1);
        assertNotNull(leagues);
        assertTrue(leagues.isEmpty());
    }

    @Test
    public void testGetByCategoryId_InvalidCategoryId() {
        initLeagues();

        List<LeagueDto> leagues = service.getByCategoryId(3);
        assertNotNull(leagues);
        assertTrue(leagues.isEmpty());
    }

    private void initLeagues() {
        LeagueCategory category1 = new LeagueCategory();
        category1.setId(1);
        category1.setName("Category 1");
        category1 = leagueCategoryDao.save(category1);

        LeagueCategory category2 = new LeagueCategory();
        category2.setId(2);
        category2.setName("Category 2");
        category2 = leagueCategoryDao.save(category2);

        League league1 = new League();
        league1.setId(1);
        league1.setName("League 1");
        league1.setCategory(category1);
        leagueDao.save(league1);

        League league2 = new League();
        league2.setId(2);
        league2.setName("League 2");
        league2.setCategory(category1);
        leagueDao.save(league2);

        League league3 = new League();
        league3.setId(3);
        league3.setName("League 3");
        league3.setCategory(category2);
        leagueDao.save(league3);
    }
}
