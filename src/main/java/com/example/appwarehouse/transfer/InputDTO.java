package com.example.appwarehouse.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDTO {

    private Timestamp date;

    private Integer wareHouse_id;

    private Integer supplier_id;

    private Integer currency_id;

    private String factureNumber;

    private String code;
}
