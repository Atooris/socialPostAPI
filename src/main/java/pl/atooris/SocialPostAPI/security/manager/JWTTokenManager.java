package pl.atooris.SocialPostAPI.security.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import pl.atooris.SocialPostAPI.security.SecurityConstants;

import java.util.Date;

@Component
public class JWTTokenManager {
    public String createVerificationToken(String subject){
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        return token;
    }

    public void verifyToken(String token){
        JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token);
    }
}
