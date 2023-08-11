package com.niit.UserAuthentication.service;

import com.niit.UserAuthentication.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtSecurityTokenImpl implements SecurityTokenGenerator{
    @Override
    public Map<String, String> generateToken(User user) {

        Map<String,String> result = new HashMap<>();
        user.setPassword("");
        Map<String,Object> userData = new HashMap<>();
        userData.put("user_email",user.getEmail());
        String jwt = Jwts.builder()
                .setClaims(userData)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"ourkey")
                .compact();
        result.put("token",jwt);
        result.put("message","User login success");
        result.put("email", user.getEmail());
        result.put("firstName", user.getFirstName());
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
        return result;
    }
}



































