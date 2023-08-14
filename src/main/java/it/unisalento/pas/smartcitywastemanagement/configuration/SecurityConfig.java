package it.unisalento.pas.smartcitywastemanagement.configuration;

import it.unisalento.pas.smartcitywastemanagement.security.JwtAuthenticationFilter;
import it.unisalento.pas.smartcitywastemanagement.service.CustomUserDetailsService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().requestMatchers("/api/users/authenticate").permitAll(). //permetti a tutti di autenticarsi, di utilizzare l'api authenticate
                anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//ogni richiesta è indipendente l'una dall'altra,mi devo autorizzare sempre,due chiamate dallo stesso utente sono indipendenti
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();


        /*return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/bins/**").permitAll() //endpoint che sono liberi
                .and()
                .httpBasic(Customizer.withDefaults())
                .build(); */
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /*@Bean
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

    }*/
}
