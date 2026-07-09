package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

// This is one reported disaster, like a flood or fire.
@Entity
public class DisasterIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String description;

    // example: FLOOD, FIRE, EARTHQUAKE, CYCLONE
    private String incidentType;

    // example: LOW, MEDIUM, HIGH, CRITICAL
    private String severityLevel;

    private String location;

    // example: REPORTED, ASSIGNED, RESOLVED, CANCELLED
    private String status;

    // name of the responder handling this incident (kept simple, just a name/username)
    private String assignedResponder;

    public DisasterIncident() {
    }

    public DisasterIncident(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedResponder() {
        return assignedResponder;
    }

    public void setAssignedResponder(String assignedResponder) {
        this.assignedResponder = assignedResponder;
    }
}
