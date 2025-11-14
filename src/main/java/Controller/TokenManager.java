package Controller;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import java.util.Date;


public class TokenManager {
    private final SecretKey key;
    private static final long EXPIRATION_TIME = 3600000;

    public TokenManager(){
        this.key =Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String issueToken(String username){
        return Jwts.builder().
                setSubject(username).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).
                signWith(key).
                compact();
    }

    public boolean validateToken(String token,String username){
        try{
            Claims claims = Jwts.parser().
                    setSigningKey(this.key).
                    build().
                    parseClaimsJws(token).
                    getBody();
            return claims.getSubject().equals(username);

        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

}