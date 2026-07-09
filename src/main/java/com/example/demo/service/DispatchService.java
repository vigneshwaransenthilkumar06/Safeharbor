package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DispatchRequestDTO;
import com.example.demo.entity.DisasterIncident;
import com.example.demo.entity.ResourceDispatch;
import com.example.demo.entity.SupplyInventory;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DisasterIncidentRepository;
import com.example.demo.repository.ResourceDispatchRepository;
import com.example.demo.repository.SupplyInventoryRepository;

@Service
public class DispatchService {

    final ResourceDispatchRepository dispatchRepository;
    final DisasterIncidentRepository incidentRepository;
    final SupplyInventoryRepository inventoryRepository;

    DispatchService(ResourceDispatchRepository dispatchRepository,
            DisasterIncidentRepository incidentRepository,
            SupplyInventoryRepository inventoryRepository) {
        this.dispatchRepository = dispatchRepository;
        this.incidentRepository = incidentRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public ResourceDispatch createDispatch(DispatchRequestDTO dto) {
        DisasterIncident incident = incidentRepository.findById(dto.getIncidentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Incident not found with id: " + dto.getIncidentId()));

        SupplyInventory item = inventoryRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory item not found with id: " + dto.getItemId()));

        if (dto.getQuantity() > item.getQuantity()) {
            throw new BadRequestException("Not enough stock. Available: " + item.getQuantity()
                    + ", Requested: " + dto.getQuantity());
        }

        // take the items out of stock right away
        item.setQuantity(item.getQuantity() - dto.getQuantity());
        inventoryRepository.save(item);

        // move incident to ASSIGNED if it was just REPORTED
        if ("REPORTED".equals(incident.getStatus())) {
            incident.setStatus("ASSIGNED");
            incidentRepository.save(incident);
        }

        ResourceDispatch dispatch = new ResourceDispatch(incident, item, dto.getQuantity());
        dispatch.setStatus("IN_TRANSIT");

        return dispatchRepository.save(dispatch);
    }

    public List<ResourceDispatch> getAll() {
        return dispatchRepository.findAll();
    }

    public ResourceDispatch getById(Long id) {
        return dispatchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatch not found with id: " + id));
    }

    public ResourceDispatch markDelivered(Long id) {
        ResourceDispatch dispatch = getById(id);

        if (!"IN_TRANSIT".equals(dispatch.getStatus())) {
            throw new BadRequestException("Only IN_TRANSIT dispatches can be delivered. Current status: "
                    + dispatch.getStatus());
        }

        dispatch.setStatus("DELIVERED");
        return dispatchRepository.save(dispatch);
    }

    public void cancelDispatch(Long id) {
        ResourceDispatch dispatch = getById(id);

        if ("DELIVERED".equals(dispatch.getStatus()) || "CANCELLED".equals(dispatch.getStatus())) {
            throw new BadRequestException("Dispatch is already " + dispatch.getStatus());
        }

        // put the stock back since it never reached the incident
        SupplyInventory item = dispatch.getItem();
        item.setQuantity(item.getQuantity() + dispatch.getQuantity());
        inventoryRepository.save(item);

        dispatch.setStatus("CANCELLED");
        dispatchRepository.save(dispatch);
    }
}
