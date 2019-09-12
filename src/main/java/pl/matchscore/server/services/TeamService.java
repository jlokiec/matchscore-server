package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.TeamDao;
import pl.matchscore.server.models.Team;
import pl.matchscore.server.models.dto.TeamDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    TeamDao dao;

    @Autowired
    public TeamService(TeamDao dao) {
        this.dao = dao;
    }

    public List<TeamDto> getAll() {
        List<TeamDto> convertedTeams = new ArrayList<>();
        List<Team> teams = dao.findAll();

        for (Team team : teams) {
            convertedTeams.add(new TeamDto(team));
        }

        return convertedTeams;
    }
}
