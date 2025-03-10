package com.mantovani.park_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUltils {

    public static final String JWT_BEARER ="Bearer";
    public static final String JWT_AUTHORIZATION ="Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789-0123456789-abcdefghijklmnopqrstuvwx";
    public static final long  EXPIRATION_DAYS = 0;
    public static final long  EXPIRATION_HOURS = 0;
    public static final long  EXPIRATION_MINUTES = 30;

    private JwtUltils(){
    }

    private static Key generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRATION_DAYS).plusHours(EXPIRATION_HOURS).plusMinutes(EXPIRATION_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date();
        Date expirationDate = toExpireDate(issuedAt);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuer("Park API")
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .claim("role", role)
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();

        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException e){
            log.error(String.format("Token invalido %s", e.getMessage()));
        }
        return null;
    }

    public static String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public static Boolean isTokenValid(String token){
        try {
             Jwts.parser()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token));

             return true;
        } catch (JwtException e){
            log.error(String.format("Token invalido %s", e.getMessage()));
        }
        return false;
    }

    private static String refactorToken(String token){
        if (token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}
