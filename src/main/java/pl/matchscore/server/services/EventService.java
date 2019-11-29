package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.EventDao;
import pl.matchscore.server.dao.ReportDao;
import pl.matchscore.server.models.Event;
import pl.matchscore.server.models.Report;
import pl.matchscore.server.models.dto.CreateEventDto;
import pl.matchscore.server.models.dto.EventDto;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;
import pl.matchscore.server.services.exceptions.UsernamesNotMatchException;

@Service
public class EventService {
    private EventDao eventDao;
    private ReportDao reportDao;

    @Autowired
    public EventService(EventDao eventDao, ReportDao reportDao) {
        this.eventDao = eventDao;
        this.reportDao = reportDao;
    }

    public EventDto create(CreateEventDto createEventDto, String username) throws ReportNotFoundException, UsernamesNotMatchException {
        Report report = reportDao.findById(createEventDto.getReportId());

        if (report == null) {
            throw new ReportNotFoundException("Report ID " + createEventDto.getReportId() + " not found.");
        }

        if (!username.equals(report.getUser().getUsername())) {
            throw new UsernamesNotMatchException("Report and event users don't match.");
        }

        Event event = new Event();
        event.setTimestamp(createEventDto.getTimestamp());
        event.setEventType(createEventDto.getEventType());
        event.setReport(report);
        event.setDescription(createEventDto.getDescription());

        return new EventDto(eventDao.save(event));
    }
}
