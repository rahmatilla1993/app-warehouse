package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.User;
import com.example.appwarehouse.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumber(Integer id, String phoneNumber);

    boolean existsByPhoneNumberAndUserName(String phoneNumber, String userName);

    boolean existsByIdIsNotAndPhoneNumberAndUserName(Integer id, String phoneNumber, String userName);

    boolean existsByUserName(String userName);

    boolean existsByIdIsNotAndUserName(Integer id, String userName);
}
