package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import it.unisalento.pas.smartcitywastemanagement.dto.FillingLevelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import it.unisalento.pas.smartcitywastemanagement.domain.MeasureWasteBin;
import it.unisalento.pas.smartcitywastemanagement.dto.MeasureWasteBinDTO;
import it.unisalento.pas.smartcitywastemanagement.repositories.MeasureWasteBinRepository;

import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")
@CrossOrigin
@RestController
@RequestMapping("/api/measurements")
public class MeasureWasteBinRestController {

    @Autowired
    MeasureWasteBinRepository measureWasteBinRepository;

    @GetMapping("/")
    public List<MeasureWasteBinDTO> getAllMeasurements() {
        List<MeasureWasteBinDTO> dtoList = new ArrayList<>();
        List<MeasureWasteBin> measurements = measureWasteBinRepository.findAll();
        for (MeasureWasteBin measurement : measurements) {
            MeasureWasteBinDTO dto = new MeasureWasteBinDTO();
            // Map properties from domain to DTO
            dto.setId(measurement.getId());
            dto.setTimestamp(measurement.getTimestamp());
            dto.setIdBin(measurement.getIdBin());
            dto.setFillingLevel(measurement.getFillingLevel());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public MeasureWasteBinDTO getMeasurementById(@PathVariable String id) {
        MeasureWasteBin measurement = measureWasteBinRepository.findById(id).orElse(null);
        if (measurement == null) {
            return null;
        }
        MeasureWasteBinDTO dto = new MeasureWasteBinDTO();
        // Map properties from domain to DTO
        dto.setTimestamp(measurement.getTimestamp());
        dto.setIdBin(measurement.getIdBin());
        dto.setFillingLevel(measurement.getFillingLevel());
        return dto;
    }



    @PutMapping("/updateFillingLevel/{idBin}")
    public ResponseEntity<MeasureWasteBinDTO> updateFillingLevel(@PathVariable String idBin, @RequestBody FillingLevelDTO fillingLevelDTO) {

        Double newFillingLevel = fillingLevelDTO.getFillingLevel();

        if (newFillingLevel == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // 400 Bad Request
        }

        MeasureWasteBin measurement = measureWasteBinRepository.findByIdBin(idBin);

        if (measurement == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
        }

        measurement.setFillingLevel(newFillingLevel);
        measureWasteBinRepository.save(measurement);

        MeasureWasteBinDTO dto = new MeasureWasteBinDTO();
        dto.setId(measurement.getId());
        dto.setTimestamp(measurement.getTimestamp());
        dto.setIdBin(measurement.getIdBin());
        dto.setFillingLevel(measurement.getFillingLevel());

        return new ResponseEntity<>(dto, HttpStatus.OK);  // 200 OK
    }
    @PostMapping("/")
    public ResponseEntity<MeasureWasteBinDTO> addMeasurement(@RequestBody MeasureWasteBinDTO measureDTO) {
        if (measureDTO == null || measureDTO.getFillingLevel() == null || measureDTO.getIdBin() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // 400 Bad Request
        }

        MeasureWasteBin newMeasurement = new MeasureWasteBin();
        newMeasurement.setIdBin(measureDTO.getIdBin());
        newMeasurement.setTimestamp(measureDTO.getTimestamp());
        newMeasurement.setFillingLevel(measureDTO.getFillingLevel());
        measureWasteBinRepository.save(newMeasurement);

        measureDTO.setId(newMeasurement.getId());

        return new ResponseEntity<>(measureDTO, HttpStatus.CREATED);  // 201 Created
    }


    // Other API endpoints for update and delete
}
