package com.example.storage.controller;

import com.example.storage.model.BuildingStorage;
import com.example.storage.service.BuildingStorageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final BuildingStorageService buildingStorageService;

    public OrderController(BuildingStorageService buildingStorageService) {
        this.buildingStorageService = buildingStorageService;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody BuildingStorage buildingStorage) {
        checker();
        buildingStorageService.makeOrder(buildingStorage);
    }

    @GetMapping("/getAll")
    public List<BuildingStorage> getAll (@RequestParam(value = "item") String item){
        checker();
        return buildingStorageService.getAll(item);
    }

    public void checker() {
        buildingStorageService.deleteOrders((System.currentTimeMillis() / 1000L) - 600L);
    }
}
