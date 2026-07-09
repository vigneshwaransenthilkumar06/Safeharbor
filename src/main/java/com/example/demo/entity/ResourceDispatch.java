package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// This is a record of some supply items being sent to a disaster incident.
@Entity
public class ResourceDispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // using EAGER here (instead of LAZY) so the incident and item details
    // come along automatically when we send this object back as JSON
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incident_id")
    private DisasterIncident incident;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id")
    private SupplyInventory item;

    private int quantity;

    // example: PENDING, IN_TRANSIT, DELIVERED, CANCELLED
    private String status;

    public ResourceDispatch() {
    }

    public ResourceDispatch(DisasterIncident incident, SupplyInventory item, int quantity) {
        this.incident = incident;
        this.item = item;
        this.quantity = quantity;
        this.status = "PENDING";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisasterIncident getIncident() {
        return incident;
    }

    public void setIncident(DisasterIncident incident) {
        this.incident = incident;
    }

    public SupplyInventory getItem() {
        return item;
    }

    public void setItem(SupplyInventory item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
