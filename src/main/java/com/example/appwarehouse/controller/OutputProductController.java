package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.OutputProduct;
import com.example.appwarehouse.service.OutputProductService;
import com.example.appwarehouse.transfer.OutputProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @GetMapping
    public List<OutputProduct> getAllOutputProducts() {
        return outputProductService.getAllOutputProducts();
    }

    @GetMapping("/{id}")
    public OutputProduct getOutputProduct(@PathVariable Integer id) {
        return outputProductService.getOutProduct(id);
    }

    @GetMapping("/byOutputId/{output_id}")
    public List<OutputProduct> getOutputProductsByOutputId(@PathVariable Integer output_id) {
        return outputProductService.getOutputProductsByOutputId(output_id);
    }

    @PostMapping
    public Result addOutputProduct(@RequestBody OutputProductDTO outputProductDTO) {
        return outputProductService.addOutputProduct(outputProductDTO);
    }

    @PutMapping("/{id}")
    public Result editOutputProduct(@PathVariable Integer id, @RequestBody OutputProductDTO outputProductDTO) {
        return outputProductService.editOutputProduct(id, outputProductDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutputProduct(@PathVariable Integer id) {
        return outputProductService.deleteOutputProduct(id);
    }
}
