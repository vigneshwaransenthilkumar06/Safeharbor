package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

// One row = one type of relief item, like water bottles or blankets.
@Entity
public class SupplyInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name must not be blank")
    private String itemName;

    // example: FOOD, WATER, MEDICAL, SHELTER, POWER
    private String category;

    private int quantity;

    private String unit;

    // if quantity goes below this number, it should be treated as low stock
    private int criticalThreshold;

    public SupplyInventory() {
    }

    public SupplyInventory(String itemName, String category, int quantity) {
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.criticalThreshold = 20;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(int criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }
}
