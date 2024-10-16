package com.suhail.Springsec.config;

import com.suhail.Springsec.Service.JWTService;
import com.suhail.Springsec.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {//which abstract class we must add the abstract method // to inherit the filter property and to excute only once for each request(like get put )

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context; // which holds all the beans created by the application

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // to client the jwt token will be  Bearer esofosfiser324353 here we want to take the token alone by cutting the bearer word
           // we get this in the request object header in the params
        String authHeader = request.getHeader("Authorization"); // it will many header but under the authorization header only the jwt token will be present
        String token = null;
        String username = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){ // to check whether it is not null and starts with bearer
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
        if(authHeader!=null && SecurityContextHolder.getContext().getAuthentication()==null){ // to make sure it is not already getting authenticated so that will validate here so it must be null to make sure it not authenticated

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username); // we can get the another service methods like this

            if(jwtService.validateToken(token,userDetails)) {
                // here once if the jwt filter get validate then UsernamePasswordAuthentication filter should execute with help of the jwt token so we writing code for that
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // to tell the authtoken about the request
                SecurityContextHolder.getContext().setAuthentication(authToken); // passing the auth token and pass the filter
            }
        }
        filterChain.doFilter(request,response);

    }

}
