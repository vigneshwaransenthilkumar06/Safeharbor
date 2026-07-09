package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.SupplyInventory;
import com.example.demo.service.InventoryService;

import jakarta.validation.Valid;

@RestController
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/inventory")
    public List<SupplyInventory> getAllItems() {
        return inventoryService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/inventory/low-stock")
    public List<SupplyInventory> getLowStockItems() {
        return inventoryService.getLowStockItems();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/inventory/{id}")
    public SupplyInventory getItemById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PostMapping("/inventory")
    public SupplyInventory addItem(@Valid @RequestBody SupplyInventory item) {
        return inventoryService.addItem(item);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PutMapping("/inventory/{id}")
    public SupplyInventory updateItem(@PathVariable Long id, @Valid @RequestBody SupplyInventory item) {
        return inventoryService.updateItem(id, item);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/inventory/{id}")
    public String deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return "Inventory item deleted successfully";
    }
}
