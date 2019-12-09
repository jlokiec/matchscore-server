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
import pl.matchscore.server.services.exceptions.*;

import java.util.ArrayList;
import java.util.List;

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

    public ReportDto create(long matchId, String username) throws UserNotFoundException, MatchNotFoundException, ReportAlreadyExistsException {
        User user = checkUser(username);
        Match match = checkMatch(matchId);

        try {
            checkReportForMatchIdAndUsername(matchId, username);
            throw new ReportAlreadyExistsException("Report for match ID " + matchId + " and username " + username + " already exists.");
        } catch (ReportNotFoundException e) {
            Report report = new Report();
            report.setUser(user);
            report.setMatch(match);

            return new ReportDto(reportDao.save(report));
        }
    }

    public List<ReportDto> getAllPending() {
        List<ReportDto> convertedReports = new ArrayList<>();
        List<Report> reports = reportDao.findByEndTimestampIsNotNullAndReportRating_RatingIsNull();

        for (Report report : reports) {
            convertedReports.add(new ReportDto(report));
        }

        return convertedReports;
    }

    public ReportDto getForMatchIdAndUsername(long matchId, String username) throws UserNotFoundException, MatchNotFoundException, ReportNotFoundException {
        User user = checkUser(username);
        Match match = checkMatch(matchId);
        Report report = checkReportForMatchIdAndUsername(matchId, username);

        return new ReportDto(report);
    }

    private User checkUser(String username) throws UserNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found.");
        }

        return user;
    }

    private Match checkMatch(long matchId) throws MatchNotFoundException {
        Match match = matchDao.findById(matchId);

        if (match == null) {
            throw new MatchNotFoundException("Match ID " + matchId + " not found.");
        }

        return match;
    }

    private Report checkReportForMatchIdAndUsername(long matchId, String username) throws ReportNotFoundException {
        Report report = reportDao.findByMatch_IdAndUser_Username(matchId, username);

        if (report == null) {
            throw new ReportNotFoundException("Report for match ID " + matchId + " and username " + username + " not found.");
        }

        return report;
    }

    public ReportDto setStartTimestamp(long reportId, long startTimestamp, String username) throws ReportNotFoundException, UsernamesNotMatchException {
        Report report = reportDao.findById(reportId);

        if (report == null) {
            throw new ReportNotFoundException("Report ID " + reportId + " not found.");
        }

        if (!username.equals(report.getUser().getUsername())) {
            throw new UsernamesNotMatchException("Report and request usernames don't match.");
        }

        report.setStartTimestamp(startTimestamp);
        return new ReportDto(reportDao.save(report));
    }

    public ReportDto setEndTimestamp(long reportId, long endTimestamp, String username) throws ReportNotFoundException, UsernamesNotMatchException {
        Report report = reportDao.findById(reportId);

        if (report == null) {
            throw new ReportNotFoundException("Report ID " + reportId + " not found.");
        }

        if (!username.equals(report.getUser().getUsername())) {
            throw new UsernamesNotMatchException("Report and request usernames don't match.");
        }

        report.setEndTimestamp(endTimestamp);
        return new ReportDto(reportDao.save(report));
    }
}
