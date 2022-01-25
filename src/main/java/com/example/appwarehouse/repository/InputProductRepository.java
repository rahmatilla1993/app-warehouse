package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InputProductRepository extends JpaRepository<InputProduct,Integer> {

    List<InputProduct> findAllByInput_Id(Integer input_id);
}
