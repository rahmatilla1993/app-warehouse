package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByName(String name);
    boolean existsByParentCategory_Id(Integer parentCategory_id);
    List<Category> getAllByParentCategory_Id(Integer parentCategory_id);
    boolean existsByIdIsNotAndName(Integer id, String name);
}
