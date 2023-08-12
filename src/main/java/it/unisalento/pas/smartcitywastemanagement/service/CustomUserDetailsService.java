package it.unisalento.pas.smartcitywastemanagement.service;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // è piu specifico di component, contiene business logic
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository; // utilizziamo nell'api , lo stiamo utilizzando solo per la parte di autorizzazzione

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // controlla solo l'utente tramite username non anche la password

        final User user = userRepository.findByUsername(username); //bata definirlo nell'interfaccia , esiste gia come metodo
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).roles("USER").build();

        return userDetails;
    }
}
