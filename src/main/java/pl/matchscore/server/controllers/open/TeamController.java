package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.TeamDto;
import pl.matchscore.server.services.TeamService;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PUBLIC_TEAMS_PATH)
public class TeamController {
    private static final String GET_ALL = "/all";

    private TeamService service;

    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping(value = GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamDto> getAll() {
        return service.getAll();
    }
}
