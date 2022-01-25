package com.example.appwarehouse.transfer;

import com.example.appwarehouse.entity.Input;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputProductDTO {

    private Integer product_id;

    private Double amount;

    private Double price;

    private Date expireDate;

    private Integer input_id;
}
