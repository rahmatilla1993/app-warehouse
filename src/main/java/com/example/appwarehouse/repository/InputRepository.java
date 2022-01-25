package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputRepository extends JpaRepository<Input,Integer> {

    List<Input> findAllByWareHouse_Id(Integer wareHouse_id);

    List<Input> findAllBySupplier_Id(Integer supplier_id);

    boolean existsByFactureNumber(String factureNumber);

    boolean existsByIdIsNotAndFactureNumber(Integer id, String factureNumber);
}
