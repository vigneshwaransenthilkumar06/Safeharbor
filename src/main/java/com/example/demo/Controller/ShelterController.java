package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.ReliefShelter;
import com.example.demo.service.ShelterService;

import jakarta.validation.Valid;

@RestController
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/shelters")
    public List<ReliefShelter> getAllShelters() {
        return shelterService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/shelters/{id}")
    public ReliefShelter getShelterById(@PathVariable Long id) {
        return shelterService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PostMapping("/shelters")
    public ReliefShelter registerShelter(@Valid @RequestBody ReliefShelter shelter) {
        return shelterService.registerShelter(shelter);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PutMapping("/shelters/{id}")
    public ReliefShelter updateShelter(@PathVariable Long id, @Valid @RequestBody ReliefShelter shelter) {
        return shelterService.updateShelter(id, shelter);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PatchMapping("/shelters/{id}/occupancy")
    public ReliefShelter adjustOccupancy(@PathVariable Long id, @RequestParam int change) {
        return shelterService.adjustOccupancy(id, change);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/shelters/{id}")
    public String deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return "Shelter deleted successfully";
    }
}
