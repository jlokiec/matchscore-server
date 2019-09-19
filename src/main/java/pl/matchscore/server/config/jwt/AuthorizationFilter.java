package pl.matchscore.server.config.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);

        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromCookies(request.getCookies());

        if (token == null) {
            return;
        }

        Jws<Claims> parsedToken = parseToken(token);

        if (parsedToken == null) {
            return;
        }

        if (isTokenAboutToExpire(parsedToken)) {
            logger.info("JWT is about to expire in less than 24 hours, generating new one.");

            byte[] signingKey = SecurityConstants.SECRET.getBytes();
            String newToken = JwtUtils.refresh(parsedToken, signingKey);
            Cookie newCookieToken = JwtCookie.create(newToken);

            response.addCookie(newCookieToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = getTokenFromCookies(request.getCookies());

        if (!Strings.isNullOrEmpty(token)) {
            Jws<Claims> parsedToken = parseToken(token);

            if (parsedToken == null) {
                return null;
            }

            String username = JwtUtils.getUsername(parsedToken);
            List<GrantedAuthority> authorities = JwtUtils.getAuthorities(parsedToken);

            if (!Strings.isNullOrEmpty(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }

        return null;
    }

    private String getTokenFromCookies(Cookie[] cookies) {
        String token = null;
        Optional<Cookie> cookie = Arrays
                .stream(cookies)
                .filter(c -> c.getName().equals(SecurityConstants.COOKIE_NAME))
                .findFirst();

        if (cookie.isPresent()) {
            token = cookie.get().getValue();
        }

        return token;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> parsedToken = null;

        try {
            byte[] signingKey = SecurityConstants.SECRET.getBytes();

            parsedToken = JwtUtils.parse(token, signingKey);
        } catch (ExpiredJwtException exception) {
            logger.info("JWT expired: " + token + " message: " + exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.info("JWT unsupported: " + token + " message: " + exception.getMessage());
        } catch (MalformedJwtException exception) {
            logger.info("JWT invalid: " + token + " message: " + exception.getMessage());
        } catch (SignatureException exception) {
            logger.info("JWT has invalid signature: " + token + " message: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.info("JWT is null or empty: " + token + " message: " + exception.getMessage());
        }

        return parsedToken;
    }

    private boolean isTokenAboutToExpire(Jws<Claims> parsedToken) {
        Date tokenExpiration = JwtUtils.getExpiration(parsedToken);
        long tokenExpirationTimestamp = tokenExpiration.getTime();
        long currentTimestamp = System.currentTimeMillis();
        long timeDiff = tokenExpirationTimestamp - currentTimestamp;

        return timeDiff <= 24 * 60 * 60 * 1000 && timeDiff > 0;
    }
}
