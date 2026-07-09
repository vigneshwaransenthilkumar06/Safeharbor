package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DisasterIncident;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DisasterIncidentRepository;

@Service
public class IncidentService {

    final DisasterIncidentRepository repository;

    IncidentService(DisasterIncidentRepository repository) {
        this.repository = repository;
    }

    public DisasterIncident reportIncident(DisasterIncident incident) {
        // every new incident starts as REPORTED
        incident.setStatus("REPORTED");
        return repository.save(incident);
    }

    public List<DisasterIncident> getAll() {
        return repository.findAll();
    }

    public DisasterIncident getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    public DisasterIncident updateIncident(Long id, DisasterIncident updated) {
        DisasterIncident existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setIncidentType(updated.getIncidentType());
        existing.setSeverityLevel(updated.getSeverityLevel());
        existing.setLocation(updated.getLocation());

        return repository.save(existing);
    }

    public DisasterIncident assignResponder(Long id, String responderName) {
        DisasterIncident incident = getById(id);
        incident.setAssignedResponder(responderName);
        incident.setStatus("ASSIGNED");
        return repository.save(incident);
    }

    public DisasterIncident updateStatus(Long id, String status) {
        DisasterIncident incident = getById(id);

        if ("RESOLVED".equals(incident.getStatus()) || "CANCELLED".equals(incident.getStatus())) {
            throw new BadRequestException("Cannot change status, incident is already " + incident.getStatus());
        }

        incident.setStatus(status);
        return repository.save(incident);
    }

    public void deleteIncident(Long id) {
        DisasterIncident incident = getById(id);
        repository.delete(incident);
    }
}
