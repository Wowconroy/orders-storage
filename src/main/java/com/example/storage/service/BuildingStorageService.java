package com.example.storage.service;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
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
        buildingStorageRepository.save(order);
    }

    public List<BuildingStorage> getAll(String item){
        boolean hasOne = buildingStorageRepository.findAll()
                .stream()
                .map(x -> x.getItem().equals(item))
                .anyMatch(i -> i.equals(true));

//        List<BuildingStorage> buildingStorages = new ArrayList<>();
//
//        buildingStorageRepository.findAll().stream()
//                .filter(value -> value.equals(item)).findAny().

        if (hasOne) {
            return new ArrayList<>(buildingStorageRepository.findByItem(item, Sort.by("price")));
        }
        return buildingStorageRepository.findAll();
    }

    @Scheduled(fixedRateString = "${spring.intervalMs}")
    public void deleteAfterTenMinutes(){
        buildingStorageRepository.deleteAll();
    }
}
