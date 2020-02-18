package pl.matchscore.server.config.jwt;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

        String token = getTokenFromRequest(request);

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

            addTokenToResponse(response, newToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = getTokenFromRequest(request);

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

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            return token.replace(SecurityConstants.TOKEN_BEARER_PREFIX, "");
        }

        return token;
    }

    private void addTokenToResponse(HttpServletResponse response, String refreshedToken) {
        response.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.TOKEN_BEARER_PREFIX + refreshedToken);
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

        return timeDiff <= SecurityConstants.TOKEN_REFRESH_THRESHOLD && timeDiff > 0;
    }
}
