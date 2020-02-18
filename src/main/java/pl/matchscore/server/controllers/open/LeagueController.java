package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.LeagueDto;
import pl.matchscore.server.services.LeagueService;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PUBLIC_LEAGUES_PATH)
public class LeagueController {
    private static final String CATEGORY_ID_PARAM = "category";

    private LeagueService service;

    @Autowired
    public LeagueController(LeagueService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LeagueDto> getAll(@RequestParam(required = false, name = CATEGORY_ID_PARAM) Long categoryId) {
        if (categoryId == null) {
            return service.getAll();
        } else {
            return service.getByCategoryId(categoryId);
        }
    }
}
