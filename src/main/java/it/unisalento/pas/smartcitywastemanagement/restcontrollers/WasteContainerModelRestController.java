package it.unisalento.pas.smartcitywastemanagement.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import it.unisalento.pas.smartcitywastemanagement.domain.WasteContainerModel;
import it.unisalento.pas.smartcitywastemanagement.dto.WasteContainerModelDTO;
import it.unisalento.pas.smartcitywastemanagement.repositories.WasteContainerModelRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasAnyRole('AGENCY', 'ADMIN')")
@RequestMapping("/api/waste-container-models")
public class WasteContainerModelRestController {

    @Autowired
    WasteContainerModelRepository wasteContainerModelRepository;

    @GetMapping("/")
    public List<WasteContainerModelDTO> getAllWasteContainerModels() {
        List<WasteContainerModelDTO> dtoList = new ArrayList<>();
        List<WasteContainerModel> models = wasteContainerModelRepository.findAll();
        for (WasteContainerModel model : models) {
            WasteContainerModelDTO dto = new WasteContainerModelDTO();
            // Map properties from domain to DTO
            dto.setId(model.getId());
            dto.setWidth(model.getWidth());
            dto.setHeight(model.getHeight());
            dto.setDepth(model.getDepth());
            dto.setCargoVolume(model.getCargoVolume());
            dto.setBrandName(model.getBrandName());
            dto.setModelName(model.getModelName());
            dto.setCompliantWith(model.getCompliantWith());
            dto.setMadeOf(model.getMadeOf());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public WasteContainerModelDTO getWasteContainerModelById(@PathVariable String id) {
        WasteContainerModel model = wasteContainerModelRepository.findById(id).orElse(null);
        if (model == null) {
            return null;
        }
        WasteContainerModelDTO dto = new WasteContainerModelDTO();
        // Map properties from domain to DTO
        dto.setId(model.getId());
        dto.setWidth(model.getWidth());
        dto.setHeight(model.getHeight());
        dto.setDepth(model.getDepth());
        dto.setCargoVolume(model.getCargoVolume());
        dto.setBrandName(model.getBrandName());
        dto.setModelName(model.getModelName());
        dto.setCompliantWith(model.getCompliantWith());
        dto.setMadeOf(model.getMadeOf());
        return dto;
    }

    @PostMapping("/")
    public WasteContainerModelDTO createWasteContainerModel(@RequestBody WasteContainerModelDTO modelDTO) {
        WasteContainerModel model = new WasteContainerModel();
        // Map properties from DTO to domain
        model.setId(modelDTO.getId());
        model.setWidth(modelDTO.getWidth());
        model.setHeight(modelDTO.getHeight());
        model.setDepth(modelDTO.getDepth());
        model.setCargoVolume(modelDTO.getCargoVolume());
        model.setBrandName(modelDTO.getBrandName());
        model.setModelName(modelDTO.getModelName());
        model.setCompliantWith(modelDTO.getCompliantWith());
        model.setMadeOf(modelDTO.getMadeOf());
        model = wasteContainerModelRepository.save(model);
        modelDTO.setId(model.getId());
        return modelDTO;
    }

    // Other API endpoints for update and delete
}
