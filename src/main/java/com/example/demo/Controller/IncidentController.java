package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.DisasterIncident;
import com.example.demo.service.IncidentService;

import jakarta.validation.Valid;

@RestController
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/incidents")
    public List<DisasterIncident> getAllIncidents() {
        return incidentService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/incidents/{id}")
    public DisasterIncident getIncidentById(@PathVariable Long id) {
        return incidentService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PostMapping("/incidents")
    public DisasterIncident reportIncident(@Valid @RequestBody DisasterIncident incident) {
        return incidentService.reportIncident(incident);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PutMapping("/incidents/{id}")
    public DisasterIncident updateIncident(@PathVariable Long id, @Valid @RequestBody DisasterIncident incident) {
        return incidentService.updateIncident(id, incident);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PatchMapping("/incidents/{id}/assign")
    public DisasterIncident assignResponder(@PathVariable Long id, @RequestParam String responderName) {
        return incidentService.assignResponder(id, responderName);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PatchMapping("/incidents/{id}/status")
    public DisasterIncident updateStatus(@PathVariable Long id, @RequestParam String status) {
        return incidentService.updateStatus(id, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/incidents/{id}")
    public String deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return "Incident deleted successfully";
    }
}
