package it.unisalento.pas.smartcitywastemanagement.repositories;

import it.unisalento.pas.smartcitywastemanagement.domain.WasteContainerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WasteContainerModelRepository extends MongoRepository<WasteContainerModel, String> {
}
