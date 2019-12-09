package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.ReportDao;
import pl.matchscore.server.dao.ReportRatingDao;
import pl.matchscore.server.dao.UserDao;
import pl.matchscore.server.models.Report;
import pl.matchscore.server.models.ReportRating;
import pl.matchscore.server.models.User;
import pl.matchscore.server.models.dto.RateReportDto;
import pl.matchscore.server.models.dto.ReportRatingDto;
import pl.matchscore.server.services.exceptions.ReportNotFoundException;
import pl.matchscore.server.services.exceptions.ReportRatedException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;

@Service
public class ReportRatingService {
    private ReportRatingDao reportRatingDao;
    private ReportDao reportDao;
    private UserDao userDao;

    @Autowired
    public ReportRatingService(ReportRatingDao reportRatingDao, ReportDao reportDao, UserDao userDao) {
        this.reportRatingDao = reportRatingDao;
        this.reportDao = reportDao;
        this.userDao = userDao;
    }

    public ReportRatingDto rateReport(RateReportDto rateReportDto, String username) throws ReportNotFoundException, UserNotFoundException, ReportRatedException {
        Report report = reportDao.findById(rateReportDto.getReportId());

        if (report == null) {
            throw new ReportNotFoundException("Report ID " + rateReportDto.getReportId() + " not found.");
        }

        if (report.getReportRating() != null) {
            throw new ReportRatedException("Report already rated.");
        }

        User ratedBy = userDao.findByUsername(username);

        if (ratedBy == null) {
            throw new UserNotFoundException("User " + username + " not found.");
        }

        ReportRating reportRating = new ReportRating();
        reportRating.setReport(report);
        reportRating.setRating(rateReportDto.getRating());
        reportRating.setRatedBy(ratedBy);
        reportRating.setRatingTimestamp(rateReportDto.getRatingTimestamp());

        return new ReportRatingDto(reportRatingDao.save(reportRating));
    }
}
