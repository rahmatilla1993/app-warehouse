package com.example.appwarehouse.transfer;

import com.example.appwarehouse.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private String name;

    private boolean active = true;

    private Integer parentCategoryId;
}
