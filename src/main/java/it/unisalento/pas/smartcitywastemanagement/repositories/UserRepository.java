package it.unisalento.pas.smartcitywastemanagement.repositories;

import it.unisalento.pas.smartcitywastemanagement.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> { // string è il tipo della chiave primaria cioe @Id

    public List<User> findByCognome(String cognome);
}
