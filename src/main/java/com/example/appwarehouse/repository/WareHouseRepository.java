package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse,Integer> {

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
