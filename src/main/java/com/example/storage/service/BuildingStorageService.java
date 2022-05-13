package com.example.storage.service;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import org.springframework.context.annotation.ComponentScan;
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
        order.setTimeStamp(System.currentTimeMillis() / 1000L);
        buildingStorageRepository.save(order);
    }

    public List<BuildingStorage> getAll(String item){
        boolean hasOne = buildingStorageRepository.findAll()
                .stream()
                .map(x -> x.getItem().equals(item))
                .anyMatch(i -> i.equals(true));
//        buildingStorageRepository.findAll().stream().map(x -> x.getItem().equals(item))
//                .findFirst().orElseGet(x -> buildingStorageRepository.findAll())
////                .findByItem(item, Sort.by("price"))
//
        if (hasOne) {
            return new ArrayList<>(buildingStorageRepository.findByItem(item, Sort.by("price")));
        }

        return buildingStorageRepository.findAll();
    }

    @Scheduled(fixedRateString = "${spring.intervalMs}")
    public void extracted(){
        buildingStorageRepository.deleteAfter(System.currentTimeMillis()/1000L);
    }
}
