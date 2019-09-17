package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.LeagueCategoryDto;
import pl.matchscore.server.services.LeagueCategoryService;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PUBLIC_LEAGUE_CATEGORIES_PATH)
public class LeagueCategoryController {
    private LeagueCategoryService service;

    @Autowired
    public LeagueCategoryController(LeagueCategoryService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LeagueCategoryDto> getAll() {
        return service.getAll();
    }
}
