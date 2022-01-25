package com.example.appwarehouse.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputProductDTO {

    private Integer product_id;

    private Double amount;

    private Double price;

    private Integer output_id;
}
