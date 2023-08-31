package it.unisalento.pas.smartcitywastemanagement.repositories;

import it.unisalento.pas.smartcitywastemanagement.domain.WasteContainer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WasteContainerRepository extends MongoRepository<WasteContainer, String> {
    List<WasteContainer> findAllByFillingLevelGreaterThan(Double fillingLevel);
}
