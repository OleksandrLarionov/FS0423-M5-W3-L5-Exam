package larionovoleksandr.gestioneeventi.security;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurator {
    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.addFilterBefore((Filter) jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());
        return httpSecurity.build();
    }
    @Bean
    PasswordEncoder getPSWEncoder(){
        return new BCryptPasswordEncoder(11);
    }

}
