package icu.sunway.causer.Common.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {

    private static final long time = 1000 * 3600 * 24 * 7;
    private static final Algorithm algorithm = Algorithm.HMAC256("celestial_archipelago");

    public static String generateToken(String id) {
        return JWT.create()
                .withClaim("id", id)
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .sign(algorithm);
    }

    public static String parseToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return null;
            }
            return decodedJWT.getClaim("id").asString();
        } catch (Exception e) {
            return null;
        }
    }
}