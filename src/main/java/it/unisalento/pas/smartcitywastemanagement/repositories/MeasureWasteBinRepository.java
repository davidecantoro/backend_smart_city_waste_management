package it.unisalento.pas.smartcitywastemanagement.repositories;

import it.unisalento.pas.smartcitywastemanagement.domain.MeasureWasteBin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureWasteBinRepository extends MongoRepository<MeasureWasteBin, String> {
    MeasureWasteBin findByIdBin(String idBin);
}
