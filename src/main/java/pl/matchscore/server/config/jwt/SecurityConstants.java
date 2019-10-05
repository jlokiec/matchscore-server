package pl.matchscore.server.config.jwt;

public final class SecurityConstants {
    public static final long TOKEN_EXPIRATION_MILLIS = 7 * 24 * 60 * 60 * 1000;
    public static final long TOKEN_REFRESH_THRESHOLD = 24 * 60 * 60 * 1000;
    public static final String TOKEN_ISSUER = "matchscore-api";
    public static final String TOKEN_AUDIENCE = "matchscore-client";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    public static final String ROLES_HEADER = "roles";
    public static final String SECRET = "4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)H+MbQeThW";
    public static final int BCRYPT_ROUNDS = 11;

    private SecurityConstants() {

    }
}
