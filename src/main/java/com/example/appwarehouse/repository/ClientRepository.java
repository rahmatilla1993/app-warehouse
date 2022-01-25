package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsByIdIsNotAndNameAndPhoneNumber(Integer id, String name, String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumber(Integer id, String phoneNumber);
}
