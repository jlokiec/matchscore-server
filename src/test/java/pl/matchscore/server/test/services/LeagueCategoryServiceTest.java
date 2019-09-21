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
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.dto.LeagueCategoryDto;
import pl.matchscore.server.services.LeagueCategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeagueCategoryServiceTest {
    @Autowired
    private LeagueCategoryDao dao;

    private LeagueCategoryService service;

    @BeforeEach
    public void init() {
        service = new LeagueCategoryService(dao);
    }

    @Test
    public void testGetAll() {
        initCategories();

        List<LeagueCategoryDto> categories = service.getAll();
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    public void testGetAll_Empty() {
        List<LeagueCategoryDto> categories = service.getAll();
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }

    private void initCategories() {
        LeagueCategory category1 = new LeagueCategory();
        category1.setId(1);
        category1.setName("Category 1");
        dao.save(category1);

        LeagueCategory category2 = new LeagueCategory();
        category2.setId(2);
        category2.setName("Category 2");
        dao.save(category2);
    }
}
