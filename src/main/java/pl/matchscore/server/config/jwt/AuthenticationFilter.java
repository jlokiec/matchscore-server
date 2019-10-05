package pl.matchscore.server.config.jwt;

import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.matchscore.server.config.ApiPaths;
import pl.matchscore.server.models.UserSecurityDetails;
import pl.matchscore.server.models.dto.CredentialsDto;
import pl.matchscore.server.models.dto.UserLoginDto;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(ApiPaths.AUTH);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        CredentialsDto credentials;

        try {
            String body = CharStreams.toString(request.getReader());
            credentials = new Gson().fromJson(body, CredentialsDto.class);
        } catch (IOException e) {
            throw new BadCredentialsException("Unable to parse request body.", e);
        } catch (JsonSyntaxException e) {
            throw new BadCredentialsException("Request body is not a valid JSON.", e);
        }

        if (credentials == null) {
            throw new BadCredentialsException("Credentials DTO is null");
        }

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) {
        UserSecurityDetails user = (UserSecurityDetails) authentication.getPrincipal();

        byte[] key = SecurityConstants.SECRET.getBytes();
        String token = JwtUtils.create(key, user.getUsername(), user.getAuthorities());

        setAuthorizationHeader(response, token);
        addUserDetailsToResponse(response, user);
    }

    private void setAuthorizationHeader(HttpServletResponse response, String token) {
        response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.TOKEN_BEARER_PREFIX + token);
    }

    private void addUserDetailsToResponse(HttpServletResponse response, UserSecurityDetails userSecurityDetails) {
        PrintWriter writer = null;

        try {
            writer = response.getWriter();
        } catch (IOException e) {
            logger.info("Unable to get HttpServletResponse writer.");
        }

        if (writer == null) {
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        writer.print(convertUserDetailsToJson(userSecurityDetails));
        writer.flush();
    }

    private String convertUserDetailsToJson(UserSecurityDetails userSecurityDetails) {
        UserLoginDto userLoginDto = new UserLoginDto(userSecurityDetails);
        return new Gson().toJson(userLoginDto);
    }
}
