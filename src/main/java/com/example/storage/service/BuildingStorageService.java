package com.example.storage.service;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        List<BuildingStorage> collect = buildingStorageRepository.findAll().stream()
                .filter(value -> value.getItem().equals(item))
                .sorted(Comparator.comparing(BuildingStorage::getPrice)).collect(Collectors.toList());

        if (collect.size()<=0){
            return buildingStorageRepository.findAll();
        }

        return collect;
//      return new ArrayList<>(buildingStorageRepository.findByItem(item, Sort.by("price")));
    }

    @Scheduled(fixedRateString = "${spring.intervalMs}")
    public void deleteAfterTenMinutes(){
        buildingStorageRepository.deleteAll();
    }
}
