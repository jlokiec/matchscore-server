package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.PlayerDto;
import pl.matchscore.server.services.PlayerService;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PUBLIC_PLAYERS_PATH)
public class PlayerController {
    private static final String TEAM_ID_PARAM = "teamId";

    private PlayerService service;

    @Autowired
    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerDto> getForTeamId(@RequestParam(name = TEAM_ID_PARAM) Long teamId) {
        return service.getForTeamId(teamId);
    }
}
