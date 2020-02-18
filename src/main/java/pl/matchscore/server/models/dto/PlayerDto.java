package pl.matchscore.server.models.dto;

import lombok.Data;
import pl.matchscore.server.models.Player;

@Data
public class PlayerDto {
    private long id;
    private String firstName;
    private String lastName;
    private int number;
    private TeamDto team;

    public PlayerDto(Player player) {
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.number = player.getNumber();
        this.team = new TeamDto(player.getTeam());
    }
}
