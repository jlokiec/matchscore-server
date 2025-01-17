package pl.matchscore.server.controllers.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.CreateEventDto;
import pl.matchscore.server.models.dto.EventDto;
import pl.matchscore.server.services.EventService;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;
import pl.matchscore.server.services.exceptions.UsernamesNotMatchException;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.SECURE_EVENTS_PATH)
public class EventsController {
    private static final String REPORT_ID_PARAM = "reportId";

    private EventService service;

    @Autowired
    public EventsController(EventService service) {
        this.service = service;
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EventDto create(@RequestBody CreateEventDto createEventDto, Principal user, HttpServletResponse response) {
        EventDto event = null;

        try {
            event = service.create(createEventDto, user.getName());
        } catch (ReportNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (UsernamesNotMatchException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        return event;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> getAllForReport(@RequestParam(name = REPORT_ID_PARAM) Long reportId) {
        return service.getAllForReport(reportId);
    }
}
