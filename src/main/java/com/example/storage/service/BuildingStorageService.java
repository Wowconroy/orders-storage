package com.example.storage.service;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BuildingStorageService {

    private final BuildingStorageRepository buildingStorageRepository;

    public BuildingStorageService(BuildingStorageRepository buildingStorageRepository) {
        this.buildingStorageRepository = buildingStorageRepository;
    }

    public void makeOrder(BuildingStorage order){
        buildingStorageRepository.deleteAfter((System.currentTimeMillis() / 1000L) - 600L);
        order.setTimeStamp(System.currentTimeMillis() / 1000L);
        buildingStorageRepository.save(order);
    }

    public List<BuildingStorage> getAll(String item){
        buildingStorageRepository.deleteAfter((System.currentTimeMillis() / 1000L) - 600L);
        boolean hasOne = buildingStorageRepository.findAll()
                .stream()
                .map(x -> x.getItem().equals(item))
                .anyMatch(i -> i.equals(true));

        if (hasOne) {
            return new ArrayList<>(buildingStorageRepository.findByItem(item, Sort.by("price")));
        }

        return buildingStorageRepository.findAll();
    }
}
