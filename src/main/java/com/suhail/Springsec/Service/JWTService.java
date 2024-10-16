package com.suhail.Springsec.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {


    private String secretkey = "";

    public JWTService() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256"); // type of algorithm
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded()); // to convert to the string
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()//builder is used to create object of class
                .claims()
                .add(claims) // adding the claims in the map
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis())) // for current time
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)) // for 10 mins
                .and()
                .signWith(getKey()) // signature of jwt
                .compact();

    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey); // which converts the string to byte
        return Keys.hmacShaKeyFor(keyBytes); // key for hmack
    }
}