package pl.matchscore.server.test.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.matchscore.server.dao.LeagueCategoryDao;
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.dto.LeagueCategoryDto;
import pl.matchscore.server.services.LeagueCategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeagueCategoryServiceTest {
    private static LeagueCategoryDao dao;
    private static LeagueCategoryService service;

    @BeforeEach
    public void init() {
        dao = mock(LeagueCategoryDao.class);
        service = new LeagueCategoryService(dao);
    }

    @AfterEach
    public void clean() {
        dao = null;
        service = null;
    }

    @Test
    public void testGetAll() {
        when(dao.findAll()).thenReturn(initCategories());

        List<LeagueCategoryDto> categories = service.getAll();

        assertEquals(3, categories.size(), "categories size is incorrect");
        assertEquals(1, categories.get(0).getId(), "category id is incorrect");
        assertEquals("Ligi centralne", categories.get(0).getName(), "category name is incorrect");
        assertEquals(2, categories.get(1).getId(), "category id is incorrect");
        assertEquals("III ligi", categories.get(1).getName(), "category name is incorrect");
        assertEquals(3, categories.get(2).getId(), "category id is incorrect");
        assertEquals("Województwo łódzkie", categories.get(2).getName(), "category name is incorrect");
    }

    @Test
    public void testGetAllEmpty() {
        when(dao.findAll()).thenReturn(new ArrayList<>());

        List<LeagueCategoryDto> categories = service.getAll();

        assertTrue(categories.isEmpty(), "categories size is incorrect");
    }

    private List<LeagueCategory> initCategories() {
        List<LeagueCategory> categories = new ArrayList<>();

        categories.add(new LeagueCategory(1, "Ligi centralne"));
        categories.add(new LeagueCategory(2, "III ligi"));
        categories.add(new LeagueCategory(3, "Województwo łódzkie"));

        return categories;
    }
}
