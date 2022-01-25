package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Supplier;
import com.example.appwarehouse.service.SupplierService;
import com.example.appwarehouse.transfer.Result;
import com.example.appwarehouse.transfer.SupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public List<Supplier> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id) {
        return supplierService.getSupplier(id);
    }

    @PostMapping
    public Result addSupplier(@RequestBody SupplierDTO supplierDTO) {
        return supplierService.addSupplier(supplierDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteSupplier(@PathVariable Integer id) {
        return supplierService.deleteSupplier(id);
    }

    @PutMapping("/{id}")
    public Result editSupplier(@PathVariable Integer id, @RequestBody SupplierDTO supplierDTO) {
        return supplierService.editSupplier(id, supplierDTO);
    }
}
