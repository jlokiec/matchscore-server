package pl.matchscore.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.matchscore.server.dao.PlayerDao;
import pl.matchscore.server.models.Player;
import pl.matchscore.server.models.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerDto> getForTeamId(long teamId) {
        List<Player> players = playerDao.findByTeam_Id(teamId);
        List<PlayerDto> convertedPlayers = new ArrayList<>();

        for (Player player : players) {
            convertedPlayers.add(new PlayerDto(player));
        }

        return convertedPlayers;
    }
}
