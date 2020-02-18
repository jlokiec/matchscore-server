package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.ReportDto;
import pl.matchscore.server.services.ReportService;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(ApiPaths.PUBLIC_REPORTS_PATH)
public class LiveReportController {
    private static final String MATCH_ID_PARAM = "matchId";

    private ReportService service;

    @Autowired
    public LiveReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping(value = "/live", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportDto getLiveReportForMatch(
            @RequestParam(name = MATCH_ID_PARAM) Long matchId,
            HttpServletResponse response
    ) {
        try {
            return service.getLiveReportForMatch(matchId);
        } catch (ReportNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }
}
