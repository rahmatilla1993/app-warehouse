package com.example.appwarehouse.transfer;

import com.example.appwarehouse.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private Integer category_id;

    private Integer file_id;

    private Integer measurement_id;

    private boolean active = true;
}
