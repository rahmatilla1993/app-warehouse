package com.example.appwarehouse.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDTO {

    private Date date;

    private Integer wareHouse_id;

    private Integer client_id;

    private Integer currency_id;

    private String factureNumber;

    private String code;
}
