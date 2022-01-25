package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumber(Integer id, String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumberAndName(Integer id, String phoneNumber, String name);
}
