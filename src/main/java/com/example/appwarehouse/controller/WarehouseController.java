package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.WareHouse;
import com.example.appwarehouse.service.WarehouseService;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @GetMapping
    public List<WareHouse> getWarehouses() {
        return warehouseService.getWarehouses();
    }

    @GetMapping("/{id}")
    public WareHouse getWarehouse(@PathVariable Integer id) {
        return warehouseService.getWarehouse(id);
    }

    @PostMapping
    public Result addWarehouse(@RequestBody WareHouse wareHouse) {
        return warehouseService.addWarehouse(wareHouse);
    }

    @DeleteMapping("/{id}")
    public Result deleteWarehouse(@PathVariable Integer id) {
        return warehouseService.deleteWarehouse(id);
    }

    @PutMapping("/{id}")
    public Result editWarehouse(@PathVariable Integer id, @RequestBody WareHouse wareHouse) {
        return warehouseService.editWarehouse(id, wareHouse);
    }
}
