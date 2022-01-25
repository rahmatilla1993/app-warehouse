package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    boolean existsByName(String name);
    boolean existsByIdIsNotAndName(Integer id, String name);
}
