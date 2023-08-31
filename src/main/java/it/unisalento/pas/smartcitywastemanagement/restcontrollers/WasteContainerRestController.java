package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import it.unisalento.pas.smartcitywastemanagement.domain.WasteContainer;
import it.unisalento.pas.smartcitywastemanagement.dto.WasteContainerDTO;
import it.unisalento.pas.smartcitywastemanagement.repositories.WasteContainerRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/waste-containers")
public class WasteContainerRestController {

    @Autowired
    WasteContainerRepository wasteContainerRepository;

    @PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")

    @GetMapping("/")
    public List<WasteContainerDTO> getAllWasteContainers() {
        List<WasteContainerDTO> dtoList = new ArrayList<>();
        List<WasteContainer> wasteContainers = wasteContainerRepository.findAll();
        for (WasteContainer container : wasteContainers) {
            WasteContainerDTO dto = new WasteContainerDTO();
            // Map properties from domain to DTO
            dto.setId(container.getId());
            dto.setType(container.getType());
            dto.setRefWasteContainerModel(container.getRefWasteContainerModel());
            dto.setSerialNumber(container.getSerialNumber());
            dto.setLatitude(container.getLatitude());
            dto.setLongitude(container.getLongitude());
            dto.setVia(container.getVia());
            dto.setFillingLevel(container.getFillingLevel());

            dtoList.add(dto);
        }
        return dtoList;
    }
    @PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")

    @GetMapping("/{id}")
    public WasteContainerDTO getWasteContainerById(@PathVariable String id) {
        WasteContainer wasteContainer = wasteContainerRepository.findById(id).orElse(null);
        if (wasteContainer == null) {
            return null;
        }
        WasteContainerDTO dto = new WasteContainerDTO();
        // Map properties from domain to DTO
        dto.setId(wasteContainer.getId());
        dto.setType(wasteContainer.getType());
        dto.setRefWasteContainerModel(wasteContainer.getRefWasteContainerModel());
        dto.setSerialNumber(wasteContainer.getSerialNumber());
        dto.setLatitude(wasteContainer.getLatitude());
        dto.setLongitude(wasteContainer.getLongitude());
        dto.setVia(wasteContainer.getVia());
        dto.setFillingLevel(wasteContainer.getFillingLevel());
        return dto;
    }

    @PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")

    @PostMapping("/")
    public WasteContainerDTO createWasteContainer(@RequestBody WasteContainerDTO wasteContainerDTO) {
        WasteContainer wasteContainer = new WasteContainer();
        // Map properties from DTO to domain
        wasteContainer.setId(wasteContainerDTO.getId());
        wasteContainer.setType(wasteContainerDTO.getType());
        wasteContainer.setRefWasteContainerModel(wasteContainerDTO.getRefWasteContainerModel());
        wasteContainer.setSerialNumber(wasteContainerDTO.getSerialNumber());
        wasteContainer.setLatitude(wasteContainerDTO.getLatitude());
        wasteContainer.setLongitude(wasteContainerDTO.getLongitude());
        wasteContainer.setVia(wasteContainerDTO.getVia());
        wasteContainer.setFillingLevel(wasteContainerDTO.getFillingLevel());
        wasteContainer = wasteContainerRepository.save(wasteContainer);
        wasteContainerDTO.setId(wasteContainer.getId());
        return wasteContainerDTO;
    }

    // Other API endpoints for update and delete
    @PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")
    @GetMapping("/full")
    public List<WasteContainerDTO> getFullWasteContainers() {
        List<WasteContainerDTO> dtoList = new ArrayList<>();
        List<WasteContainer> wasteContainers = wasteContainerRepository.findAllByFillingLevelGreaterThan(0.8);
        for (WasteContainer container : wasteContainers) {
            WasteContainerDTO dto = new WasteContainerDTO();
            // Map properties from domain to DTO
            dto.setId(container.getId());
            dto.setType(container.getType());
            dto.setRefWasteContainerModel(container.getRefWasteContainerModel());
            dto.setSerialNumber(container.getSerialNumber());
            dto.setLatitude(container.getLatitude());
            dto.setLongitude(container.getLongitude());
            dto.setVia(container.getVia());
            dto.setFillingLevel(container.getFillingLevel());

            dtoList.add(dto);
        }
        return dtoList;
    }

}
