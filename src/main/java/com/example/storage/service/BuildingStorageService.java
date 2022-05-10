package com.example.storage.service;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BuildingStorageService {

    private final BuildingStorageRepository buildingStorageRepository;

    public BuildingStorageService(BuildingStorageRepository buildingStorageRepository) {
        this.buildingStorageRepository = buildingStorageRepository;
    }

    public void makeOrder(BuildingStorage order){
        order.setTimeStamp(System.currentTimeMillis() / 1000L);
        buildingStorageRepository.save(order);
    }

    public List<BuildingStorage> getAll(String item){
        return buildingStorageRepository.findAll(Sort.by("price"));
    }

    public void deleteOrders(long currentTime){
        buildingStorageRepository.deleteAfter(currentTime);
    }
}
