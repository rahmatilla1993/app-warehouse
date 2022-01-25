package com.example.appwarehouse.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private String name;

    private boolean active = true;

    private String phoneNumber;
}
