package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.dto.AuthenticationResponseDTO;
import it.unisalento.pas.smartcitywastemanagement.dto.LoginDTO;
import it.unisalento.pas.smartcitywastemanagement.dto.UserDTO;
import it.unisalento.pas.smartcitywastemanagement.exceptions.UserNotFoundException;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import it.unisalento.pas.smartcitywastemanagement.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.unisalento.pas.smartcitywastemanagement.configuration.SecurityConfig.passwordEncoder;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager; // lo prende dal bean in SecurityConfig

    @Autowired
    private JwtUtilities jwtUtilities;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<UserDTO> getAll() {

        List<UserDTO> utenti = new ArrayList<>();

        for(User user : userRepository.findAll()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setNome(user.getNome());
            userDTO.setCognome(user.getCognome());
            userDTO.setEmail(user.getEmail());
            userDTO.setEta(user.getEta());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            utenti.add(userDTO);
        }
        System.out.println("ciao");
        return utenti;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UserDTO get(@PathVariable String id)  throws UserNotFoundException {

        System.out.println("ARRIVATO L'ID: "+id);

        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isPresent()) {
            User user = optUser.get();

            UserDTO user1 = new UserDTO();
            user1.setNome(user.getNome());
            user1.setCognome(user.getCognome());
            user1.setEmail(user.getEmail());
            user1.setEta(user.getEta());
            user1.setId(user.getId());
            user1.setRole(user.getRole());

            return user1;
        }
        //throw new UserNotFoundException();
        else {

            UserDTO user1 = new UserDTO();
            user1.setNome("ciao");
            user1.setCognome("ciao");
            user1.setEmail("ciao");
            user1.setEta(40);
            user1.setId("ciao");

            return user1;
        }
    }

    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO post(@RequestBody UserDTO userDTO) {

        User newUser = new User();
        newUser.setNome(userDTO.getNome()); // DTO è usato per trasferire dati tipo dalla richeista post con posteman viene mandato al DTO
        newUser.setCognome(userDTO.getCognome());
        newUser.setEmail(userDTO.getEmail());
        newUser.setEta(userDTO.getEta());
        newUser.setRole(userDTO.getRole());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder().encode(userDTO.getPassword()));

        newUser = userRepository.save(newUser);
        System.out.println("L'ID DEL NUOVO UTENTE E': "+newUser.getId());

        userDTO.setId(newUser.getId());
        userDTO.setPassword(null);
        return userDTO;
    }

    @RequestMapping(value="/find", method = RequestMethod.GET)
    public List<UserDTO> findByCognome(@RequestParam String cognome) {

        List<User> result = userRepository.findByCognome(cognome);
        List<UserDTO> utenti = new ArrayList<>();

        for(User user: result) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setNome(user.getNome());
            userDTO.setCognome(user.getCognome());
            userDTO.setEmail(user.getEmail());
            userDTO.setEta(user.getEta());
            userDTO.setRole(user.getRole());
            utenti.add(userDTO);
        }

        return utenti;
    }

    @RequestMapping(value="/authenticate", method = RequestMethod.POST) // un utente invia le credenziali a /authenticate e viene creato il LoginDT

    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        User user = userRepository.findByUsername(authentication.getName());

        if(user == null) {
            throw new UsernameNotFoundException(loginDTO.getUsername());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String role = user.getRole();

        final String jwt = jwtUtilities.generateToken(user.getUsername(), role); // Aggiunto il ruolo nella generazione del token

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }
}
// form ---> userDto ----> user ---> mongodb (attraverso userRepository )
//mongo db ---> user ( attraverso userRepository) ---> userDTO
// userRepository accede al db con i metodi preimpostati, e restituisce un User perche nell'interfaccia abbiamo messo <User, String>