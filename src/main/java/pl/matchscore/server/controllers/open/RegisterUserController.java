package pl.matchscore.server.controllers.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.dto.UserRegistrationDto;
import pl.matchscore.server.services.UserService;
import pl.matchscore.server.services.exceptions.EmailTakenException;
import pl.matchscore.server.services.exceptions.UserNotFoundException;
import pl.matchscore.server.services.exceptions.UsernameTakenException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPaths.PUBLIC_REGISTER_PATH)
public class RegisterUserController {
    private UserService service;

    @Autowired
    public RegisterUserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody @Valid UserRegistrationDto userDto, HttpServletResponse response) {
        try {
            service.register(userDto);
        } catch (UsernameTakenException | EmailTakenException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @GetMapping(value = "/confirm")
    public void confirm(@RequestParam String uuid, HttpServletResponse response) {
        try {
            service.completeRegistration(uuid);
        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
