package jwt;

public interface JwtProperties {
    String SECRET = "JangheeInsta";
    long EXPIRATION_TIME = 60000*60*60*24*7;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
