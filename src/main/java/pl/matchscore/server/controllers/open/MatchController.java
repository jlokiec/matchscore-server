package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.MatchDto;
import pl.matchscore.server.services.MatchService;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PUBLIC_MATCHES_PATH)
public class MatchController {
    private static final String LEAGUE_ID_PARAM = "league";
    private static final String KICK_OFF_DATE_PARAM = "date";

    private MatchService service;

    @Autowired
    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchDto> getAllForLeague(
            @RequestParam(required = false, name = LEAGUE_ID_PARAM) Long leagueId,
            @RequestParam(required = false, name = KICK_OFF_DATE_PARAM) Long timestamp
    ) {
        if (timestamp != null) {
            if (leagueId != null) {
                return service.getByLeagueIdAndKickOffDate(leagueId, timestamp);
            } else {
                return service.getByKickOffDate(timestamp);
            }
        } else {
            if (leagueId != null) {
                return service.getByLeagueId(leagueId);
            } else {
                return service.getAll();
            }
        }
    }
}
