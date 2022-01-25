package com.example.appwarehouse.transfer;

import com.example.appwarehouse.entity.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String code;

    private String password;

    private boolean active = true;

    private String userName;

    private Set<Integer> wareHousesIds;
}
