package pl.matchscore.server.models.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import pl.matchscore.server.models.UserSecurityDetails;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserLoginDto {
    private String username;
    private List<String> roles;

    public UserLoginDto() {
    }

    public UserLoginDto(UserSecurityDetails userSecurityDetails) {
        this.username = userSecurityDetails.getUsername();
        this.roles = userSecurityDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
