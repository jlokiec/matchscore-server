package pl.matchscore.server.config;

public final class ApiPaths {
    public static final String PUBLIC = "/public";
    public static final String SECURE = "/secure";
    public static final String PUBLIC_ANT_MATCHER = PUBLIC + "/**";
    public static final String AUTH = "/auth";
    public static final String PUBLIC_LEAGUE_CATEGORIES_PATH = PUBLIC + "/categories";
    public static final String PUBLIC_LEAGUES_PATH = PUBLIC + "/leagues";
    public static final String PUBLIC_TEAMS_PATH = PUBLIC + "/teams";
    public static final String PUBLIC_REGISTER_PATH = PUBLIC + "/register";
    public static final String PUBLIC_MATCHES_PATH = PUBLIC + "/matches";
    public static final String SECURE_REPORTS_PATH = SECURE + "/reports";
    public static final String SECURE_EVENTS_PATH = SECURE + "/events";
    public static final String SECURE_REPORT_RATINGS_PATH = SECURE + "/ratings";
    public static final String PUBLIC_PLAYERS_PATH = PUBLIC + "/players";
    public static final String PUBLIC_REPORTS_PATH = PUBLIC + "/reports";

    private ApiPaths() {

    }
}
