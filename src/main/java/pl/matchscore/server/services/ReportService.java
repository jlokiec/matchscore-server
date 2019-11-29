package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.MatchDao;
import pl.matchscore.server.dao.ReportDao;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.Match;
import pl.matchscore.server.models.Report;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.dto.ReportDto;
import pl.matchscore.server.services.exceptions.MatchNotFoundException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;

import java.time.Instant;

@Service
public class ReportService {
    private ReportDao reportDao;
    private MatchDao matchDao;
    private UserDao userDao;

    @Autowired
    public ReportService(ReportDao reportDao, MatchDao matchDao, UserDao userDao) {
        this.reportDao = reportDao;
        this.matchDao = matchDao;
        this.userDao = userDao;
    }

    public ReportDto create(long matchId, String username) throws UserNotFoundException, MatchNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found.");
        }

        Match match = matchDao.findById(matchId);

        if (match == null) {
            throw new MatchNotFoundException("Match ID " + matchId + " not found.");
        }

        Report report = new Report();
        report.setUser(user);
        report.setStartTimestamp(Instant.now().getEpochSecond());
        report.setMatch(match);

        return new ReportDto(reportDao.save(report));
    }
}
