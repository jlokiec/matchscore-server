package pl.matchscore.server.models.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {
    @Size(min = 3, max = 32)
    private String username;

    @Email
    @Size(min = 5, max = 32)
    private String email;

    @Size(min = 3, max = 32)
    private String firstName;

    @Size(min = 3, max = 32)
    private String lastName;

    @Size(min = 8, max = 32)
    private String password;
}
