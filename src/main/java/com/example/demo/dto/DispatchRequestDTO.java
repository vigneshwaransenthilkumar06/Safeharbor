package com.example.demo.dto;

// used when we want to send some supply items to an incident
public class DispatchRequestDTO {
    private Long incidentId;
    private Long itemId;
    private int quantity;

    public DispatchRequestDTO() {
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
