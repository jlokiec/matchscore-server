package pl.matchscore.server.controllers.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.RateReportDto;
import pl.matchscore.server.models.dto.ReportRatingDto;
import pl.matchscore.server.services.ReportRatingService;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;
import pl.matchscore.server.services.exceptions.ReportRatedException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping(ApiPaths.SECURE_REPORT_RATINGS_PATH)
public class ReportRatingController {
    private ReportRatingService service;

    @Autowired
    public ReportRatingController(ReportRatingService service) {
        this.service = service;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportRatingDto create(@RequestBody RateReportDto rateReportDto, Principal user, HttpServletResponse response) {
        ReportRatingDto reportRating = null;

        try {
            reportRating = service.rateReport(rateReportDto, user.getName());
        } catch (ReportNotFoundException | UserNotFoundException | ReportRatedException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return reportRating;
    }
}
