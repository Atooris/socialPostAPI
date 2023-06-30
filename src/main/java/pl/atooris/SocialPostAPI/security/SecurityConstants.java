package pl.atooris.SocialPostAPI.security;

public class SecurityConstants {
    public static final String DOMAIN_PATH = "http://localhost:8080/";
    public static final int TOKEN_EXPIRATION = 7200000; //2 hours
    public static final String SECRET_KEY = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAmhoC68n8";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_GUEST = "GUEST";
}
