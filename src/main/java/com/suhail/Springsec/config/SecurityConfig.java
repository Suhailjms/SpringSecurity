package com.suhail.Springsec.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()). // to disable the csrf token
                authorizeHttpRequests(request -> request
                .requestMatchers("register","login")
                .permitAll() // here for the register and login they won't get authenticated
                .anyRequest().authenticated()). // to have each request authenticated
                httpBasic(Customizer.withDefaults()).  // for login
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build(); // to make the session stateless where session id doesn't get stored


    }
    // there is some default one but here we are changing that
    // to change the authentication provider which authenticates the obj
    @Bean
    public AuthenticationProvider authenticationProvider(){  // this authenticationProvider responsible for to make authenticated ob
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //dao is class that extends from the interface AuthenticationProvider
        provider.setPasswordEncoder(new BCryptPasswordEncoder(13)); //here the actual password gets hashed to get the values from the database  // this now doesn't require password
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();  // authenticationManager will communicate to the authentication Provider
    }

//    @Bean
//      public UserDetailsService userDetailsService(){  // for logging in with multiple users here the userDetailsservice is a interface so we can't create obj for interface
//         UserDetails user1 = User
//                 .withDefaultPasswordEncoder()
//                 .username("Sms")
//                 .password("m@7")
//                 .roles("USER")
//                 .build();  // here build creates this particular obj
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("Ms")
//                .password("s@7")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2); // so we are creating obj for class that extend the interface so the prop of interface will get inherit to the class
//    }
}

//@Configuration // to tell the bean that this is configuration file
//@EnableWebSecurity // to prevent the default security and to enable the security mentioning here
// filterChain is the default security checks we are here gng to modify it
//        http.csrf(customizer -> customizer.disable());
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults());
//
//        return http.build();
//
//    }
//}
