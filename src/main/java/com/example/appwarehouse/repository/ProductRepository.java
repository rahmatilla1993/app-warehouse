package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByNameAndCategoryId(String name, Integer category_id);

    List<Product> findAllByCategory_Id(Integer category_id);

    boolean existsByIdIsNotAndNameAndCategory_Id(Integer id, String name, Integer category_id);
}
