package pl.matchscore.server.config.jwt;

import javax.servlet.http.Cookie;

public class JwtCookie {
    public static Cookie create(String token) {
        Cookie cookie = new Cookie(SecurityConstants.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SecurityConstants.COOKIE_MAX_AGE);
        cookie.setPath(SecurityConstants.COOKIE_PATH);
//        uncomment when https is configured
//        cookie.setSecure(true);
        return cookie;
    }
}
