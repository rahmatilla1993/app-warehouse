package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.OutputProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutputProductRepository extends JpaRepository<OutputProduct,Integer> {

    List<OutputProduct> getAllByOutput_Id(Integer output_id);
}
