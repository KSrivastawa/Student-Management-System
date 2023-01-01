package com.sms.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtAuthenticationUtils {

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration.time}")
    private long EXPIRATION;

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512 , JWT_SECRET_KEY)
                .compact();
    }

    public String getUserName(String token){

        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws( token )
                .getBody()
                .getSubject();


    }

    public String getJwtTokenFromRequest( HttpServletRequest request ){

        String bearer = request.getHeader( "Authorization" );

        try {

            if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {

                String token = bearer.substring(7);

                return token;
            }
        }
        catch(Exception e){
            throw new RuntimeException( "Invalid token! Looking suspicious" );
        }


        return null;

    }

    public Boolean validateToken(String token){

        try{
            Jwts.parser().setSigningKey( JWT_SECRET_KEY ).parseClaimsJws(token);
            return true;
        }
        catch ( Exception e ){

            throw new AuthenticationCredentialsNotFoundException( "Token was expired or incorrect" );

        }

    }
}
