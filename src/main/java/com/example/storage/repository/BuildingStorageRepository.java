package com.example.storage.repository;

import com.example.storage.model.BuildingStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuildingStorageRepository extends JpaRepository<BuildingStorage, Long>{

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM BuildingStorage b WHERE b.timestamp < :currentTime")
    void deleteAfter(@Param("currentTime") long currentTime);
}
