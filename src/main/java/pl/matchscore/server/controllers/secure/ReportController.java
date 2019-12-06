package pl.matchscore.server.controllers.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.CreateReportDto;
import pl.matchscore.server.models.dto.ReportDto;
import pl.matchscore.server.models.dto.UnratedReportDto;
import pl.matchscore.server.services.ReportService;
import pl.matchscore.server.services.exceptions.MatchNotFoundException;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.SECURE_REPORTS_PATH)
public class ReportController {
    private static final String MATCH_ID_PARAM = "matchId";

    private ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportDto create(@RequestBody CreateReportDto createReportDto, Principal user, HttpServletResponse response) {
        ReportDto report = null;

        try {
            report = service.create(createReportDto.getMatchId(), user.getName());
        } catch (UserNotFoundException | MatchNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return report;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/unrated", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UnratedReportDto> getAllUnrated() {
        return service.getAllUnrated();
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportDto getForMatchIdAndUsername(
            @RequestParam(required = false, name = MATCH_ID_PARAM) Long matchId,
            Principal user,
            HttpServletResponse response
    ) {
        if (matchId != null) {
            try {
                return service.getForMatchIdAndUsername(matchId, user.getName());
            } catch (UserNotFoundException | MatchNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (ReportNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        return null;
    }
}
