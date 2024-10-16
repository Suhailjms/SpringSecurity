package com.suhail.Springsec.Service;

import com.suhail.Springsec.Model.Users;
import com.suhail.Springsec.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
       return user;
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        // basically here we pass the unauthenticated data and gets authenticated;

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        else {
            return "fails";
        }
    }
}
