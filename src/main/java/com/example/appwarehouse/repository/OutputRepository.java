package com.example.appwarehouse.repository;

import com.example.appwarehouse.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutputRepository extends JpaRepository<Output,Integer> {

    List<Output> findAllByWareHouse_Id(Integer wareHouse_id);

    List<Output> findAllByClient_Id(Integer client_id);


}
