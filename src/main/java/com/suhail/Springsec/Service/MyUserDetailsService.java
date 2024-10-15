package com.suhail.Springsec.Service;

import com.suhail.Springsec.Model.UserDetail;
import com.suhail.Springsec.Model.Users;
import com.suhail.Springsec.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user==null){
            System.out.println("error");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserDetail(user);
    }
}
