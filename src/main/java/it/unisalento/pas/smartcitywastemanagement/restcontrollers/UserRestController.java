package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import it.unisalento.pas.smartcitywastemanagement.dto.UserDTO;
import it.unisalento.pas.smartcitywastemanagement.exceptions.UserNotFoundException;
import it.unisalento.pas.smartcitywastemanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

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
            utenti.add(userDTO);
        }

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
        newUser.setNome(userDTO.getNome());
        newUser.setCognome(userDTO.getCognome());
        newUser.setEmail(userDTO.getEmail());
        newUser.setEta(userDTO.getEta());

        newUser = userRepository.save(newUser);
        System.out.println("L'ID DEL NUOVO UTENTE E': "+newUser.getId());

        userDTO.setId(newUser.getId());
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
            utenti.add(userDTO);
        }

        return utenti;
    }
}
