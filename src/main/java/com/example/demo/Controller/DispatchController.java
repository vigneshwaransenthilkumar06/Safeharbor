package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.DispatchRequestDTO;
import com.example.demo.entity.ResourceDispatch;
import com.example.demo.service.DispatchService;

@RestController
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/dispatches")
    public List<ResourceDispatch> getAllDispatches() {
        return dispatchService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @GetMapping("/dispatches/{id}")
    public ResourceDispatch getDispatchById(@PathVariable Long id) {
        return dispatchService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PostMapping("/dispatches")
    public ResourceDispatch createDispatch(@RequestBody DispatchRequestDTO dto) {
        return dispatchService.createDispatch(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESPONDER')")
    @PatchMapping("/dispatches/{id}/deliver")
    public ResourceDispatch markDelivered(@PathVariable Long id) {
        return dispatchService.markDelivered(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/dispatches/{id}")
    public String cancelDispatch(@PathVariable Long id) {
        dispatchService.cancelDispatch(id);
        return "Dispatch cancelled successfully";
    }
}
