package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.SupplyInventory;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyInventoryRepository;

@Service
public class InventoryService {

    final SupplyInventoryRepository repository;

    InventoryService(SupplyInventoryRepository repository) {
        this.repository = repository;
    }

    public SupplyInventory addItem(SupplyInventory item) {
        return repository.save(item);
    }

    public List<SupplyInventory> getAll() {
        return repository.findAll();
    }

    public List<SupplyInventory> getLowStockItems() {
        return repository.findLowStockItems();
    }

    public SupplyInventory getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + id));
    }

    public SupplyInventory updateItem(Long id, SupplyInventory updated) {
        SupplyInventory existing = getById(id);

        existing.setItemName(updated.getItemName());
        existing.setCategory(updated.getCategory());
        existing.setQuantity(updated.getQuantity());
        existing.setUnit(updated.getUnit());
        existing.setCriticalThreshold(updated.getCriticalThreshold());

        return repository.save(existing);
    }

    // reduce stock, used when supplies are sent out
    public void reduceStock(Long id, int amount) {
        SupplyInventory item = getById(id);

        if (amount > item.getQuantity()) {
            throw new BadRequestException("Not enough stock for " + item.getItemName()
                    + ". Available: " + item.getQuantity() + ", Requested: " + amount);
        }

        item.setQuantity(item.getQuantity() - amount);
        repository.save(item);
    }

    // add stock back, used when a dispatch is cancelled
    public void addStock(Long id, int amount) {
        SupplyInventory item = getById(id);
        item.setQuantity(item.getQuantity() + amount);
        repository.save(item);
    }

    public void deleteItem(Long id) {
        SupplyInventory item = getById(id);
        repository.delete(item);
    }
}
