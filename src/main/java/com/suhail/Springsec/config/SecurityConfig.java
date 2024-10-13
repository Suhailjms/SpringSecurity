package com.suhail.Springsec.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()). // to disable the csrf token
                authorizeHttpRequests(request -> request.anyRequest().authenticated()). // to have each request authenticated
                httpBasic(Customizer.withDefaults()).  // for login
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build(); // to make the session stateless where session id doesn't get stored


    }
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
