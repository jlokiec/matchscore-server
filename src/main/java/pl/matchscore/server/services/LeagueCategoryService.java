package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.LeagueCategoryDao;
import pl.matchscore.server.models.LeagueCategory;
import pl.matchscore.server.models.dto.LeagueCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueCategoryService {
    private LeagueCategoryDao dao;

    @Autowired
    public LeagueCategoryService(LeagueCategoryDao dao) {
        this.dao = dao;
    }

    public List<LeagueCategoryDto> getAll() {
        List<LeagueCategoryDto> convertedCategories = new ArrayList<>();
        List<LeagueCategory> categories = dao.findAll();

        for (LeagueCategory category : categories) {
            convertedCategories.add(new LeagueCategoryDto(category));
        }

        return convertedCategories;
    }
}
