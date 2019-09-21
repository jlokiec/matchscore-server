package pl.matchscore.server.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
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

    public static String create(byte[] key, String username, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

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

    public static Jws<Claims> parse(String token, byte[] signingKey) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token);
    }

    public static String refresh(Jws<Claims> parsedOldToken, byte[] signingKey) {
        String username = JwtUtils.getUsername(parsedOldToken);
        List<GrantedAuthority> authorities = JwtUtils.getAuthorities(parsedOldToken);
        return JwtUtils.create(signingKey, username, authorities);
    }
}
