package pl.matchscore.server.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {
    public static List<GrantedAuthority> getAuthorities(Jws<Claims> parsedToken) {
        List<GrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get(SecurityConstants.ROLES_HEADER))
                .stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());

        return authorities;
    }

    public static String getUsername(Jws<Claims> parsedToken) {
        return parsedToken.getBody().getSubject();
    }

    public static Date getExpiration(Jws<Claims> parsedToken) {
        return parsedToken.getBody().getExpiration();
    }

    public static String create(byte[] key, String username, List<String> roles) {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS512)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_MILLIS))
                .claim(SecurityConstants.ROLES_HEADER, roles)
                .compact();

        return token;
    }
}
