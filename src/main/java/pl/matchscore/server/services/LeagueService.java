package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.LeagueDao;
import pl.matchscore.server.models.League;
import pl.matchscore.server.models.dto.LeagueDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {
    private LeagueDao dao;

    @Autowired
    public LeagueService(LeagueDao dao) {
        this.dao = dao;
    }

    public List<LeagueDto> getAll() {
        List<LeagueDto> convertedLeagues = new ArrayList<>();
        List<League> leagues = dao.findAll();

        for (League league : leagues) {
            convertedLeagues.add(new LeagueDto(league));
        }

        return convertedLeagues;
    }

    public List<LeagueDto> getByCategoryId(long categoryId) {
        List<LeagueDto> convertedLeagues = new ArrayList<>();
        List<League> leagues = dao.findAllByCategory_Id(categoryId);

        for (League league : leagues) {
            convertedLeagues.add(new LeagueDto(league));
        }

        return convertedLeagues;
    }
}
