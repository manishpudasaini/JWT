package com.jwt.learnjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    /*
    *256 bit encryption key
    * generated from the website - https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
     */
    private final String SECRETE_KEY = "432646294A404E635266556A586E327235753878214125442A472D4B61506453";

    //this function is used to extract the usrername
    public String extractUsername(String token) {
        //here subject is our username
        return extractSingClaims(token,Claims::getSubject);
    }

    /*
    * we use this function to generate token
     */
    public String generateToken(Map<String,Object> extractClais, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extractClais)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(generateSigInScretekey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //this function is used to generate the token by passing UserDetails in parameter
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //this function is used to validate the token that the token is valid or not
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //this method is used to check if the token is expired or not
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //this method is used to extract expiration date of token
    private Date extractExpiration(String token) {
        return extractSingClaims(token, Claims::getExpiration);

    }
    /*
    *Claims means all the field in the payload of jwt token
    * so we use this method to extract all the claims from  the token we generated
    * sub, authorize role etc
     */
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(generateSigInScretekey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
    * this function is used to extract all the claims seperately
    * we use this function to extract username from the token
    * use to extract expiration from the token
     */
    public <T> T extractSingClaims(String token, Function<Claims,T> claimResolver){
        //we get all the claims of the token her
        final Claims  claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    /*
    * this key is used to create the token - verify signature field
    * this key is the proof that the user is same who send the message
    * this key should be decoded using Base64
     */
    private Key generateSigInScretekey() {
        byte[] secreteKey = Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(secreteKey);
    }


}
