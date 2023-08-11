package it.unisalento.pas.smartcitywastemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/bins/**").permitAll() //endpoint che sono liberi
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails roberto = User.builder() // User non è la classe che abbiamo creato noi ma è un altra classe
                .username("roberto")
                .password(passwordEncoder().encode("12345")) // la password codificata viene messa nell'header del pacchetto ip
                .roles("ADMIN")
                .build();

        UserDetails paolo = User.builder()
                .username("paolo")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(roberto, paolo);

    }
}
