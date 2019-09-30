package pl.matchscore.server.controllers.open;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.config.jwt.JwtCookie;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(ApiPaths.LOGOUT)
public class LogoutController {
    @GetMapping(value = "")
    public void logout(HttpServletResponse response) {
        response.addCookie(JwtCookie.delete());
    }
}
