package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Integer> {

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
