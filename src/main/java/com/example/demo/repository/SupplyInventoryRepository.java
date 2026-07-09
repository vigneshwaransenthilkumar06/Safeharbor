package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.SupplyInventory;

public interface SupplyInventoryRepository extends JpaRepository<SupplyInventory, Long> {

    // finds items where quantity has gone below the critical threshold
    @Query("select s from SupplyInventory s where s.quantity <= s.criticalThreshold")
    List<SupplyInventory> findLowStockItems();
}
