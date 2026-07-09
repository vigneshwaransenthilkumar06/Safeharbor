package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ReliefShelter;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ReliefShelterRepository;

@Service
public class ShelterService {

    final ReliefShelterRepository repository;

    ShelterService(ReliefShelterRepository repository) {
        this.repository = repository;
    }

    public ReliefShelter registerShelter(ReliefShelter shelter) {
        shelter.setCurrentOccupancy(0);
        return repository.save(shelter);
    }

    public List<ReliefShelter> getAll() {
        return repository.findAll();
    }

    public ReliefShelter getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with id: " + id));
    }

    public ReliefShelter updateShelter(Long id, ReliefShelter updated) {
        ReliefShelter existing = getById(id);

        existing.setShelterName(updated.getShelterName());
        existing.setAddress(updated.getAddress());
        existing.setCapacity(updated.getCapacity());
        existing.setManagerName(updated.getManagerName());

        return repository.save(existing);
    }

    // used to add or remove people from a shelter
    // pass a positive number to add people, negative number to remove people
    public ReliefShelter adjustOccupancy(Long id, int change) {
        ReliefShelter shelter = getById(id);

        int newOccupancy = shelter.getCurrentOccupancy() + change;

        if (newOccupancy > shelter.getCapacity()) {
            throw new BadRequestException("Shelter is full. Capacity: " + shelter.getCapacity()
                    + ", Current: " + shelter.getCurrentOccupancy());
        }
        if (newOccupancy < 0) {
            throw new BadRequestException("Occupancy cannot go below zero");
        }

        shelter.setCurrentOccupancy(newOccupancy);
        return repository.save(shelter);
    }

    public void deleteShelter(Long id) {
        ReliefShelter shelter = getById(id);
        repository.delete(shelter);
    }
}
