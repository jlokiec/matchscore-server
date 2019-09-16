package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.MatchDao;
import pl.matchscore.server.models.Match;
import pl.matchscore.server.models.dto.MatchDto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private MatchDao dao;

    @Autowired
    public MatchService(MatchDao dao) {
        this.dao = dao;
    }

    public List<MatchDto> getByLeagueId(long leagueId) {
        List<Match> matches = dao.findByLeague_Id(leagueId);

        return convertMatchesToDto(matches);
    }

    public List<MatchDto> getByKickOffDate(long kickOffTimestamp) {
        long start = getStartDateTimestamp(kickOffTimestamp);
        long end = getEndDateTimestamp(kickOffTimestamp);

        List<Match> matches = dao.findByKickOffTimestampBetween(start, end);

        return convertMatchesToDto(matches);
    }

    public List<MatchDto> getByLeagueIdAndKickOffDate(long leagueId, long kickOffTimestamp) {
        long start = getStartDateTimestamp(kickOffTimestamp);
        long end = getEndDateTimestamp(kickOffTimestamp);

        List<Match> matches = dao.findByLeague_IdAndKickOffTimestampBetween(leagueId, start, end);

        return convertMatchesToDto(matches);
    }

    public List<MatchDto> getAll() {
        List<Match> matches = dao.findAll();

        return convertMatchesToDto(matches);
    }

    private long getStartDateTimestamp(long timestamp) {
        Instant timestampInstant = Instant.ofEpochSecond(timestamp);
        ZoneId timezoneId = ZoneId.systemDefault();
        ZonedDateTime timestampDate = ZonedDateTime.ofInstant(timestampInstant, timezoneId);
        ZonedDateTime startOfDay = timestampDate.toLocalDate().atStartOfDay(timezoneId);
        return Instant.from(startOfDay).getEpochSecond();
    }

    private long getEndDateTimestamp(long timestamp) {
        Instant timestampInstant = Instant.ofEpochSecond(timestamp);
        ZoneId timezoneId = ZoneId.systemDefault();
        ZonedDateTime timestampDate = ZonedDateTime.ofInstant(timestampInstant, timezoneId);
        ZonedDateTime startOfDay = timestampDate.toLocalDate().atStartOfDay(timezoneId).plusDays(1).minusSeconds(1);
        return Instant.from(startOfDay).getEpochSecond();
    }

    private List<MatchDto> convertMatchesToDto(List<Match> matches) {
        List<MatchDto> convertedMatches = new ArrayList<>();

        for (Match match : matches) {
            convertedMatches.add(new MatchDto(match));
        }

        return convertedMatches;
    }
}
