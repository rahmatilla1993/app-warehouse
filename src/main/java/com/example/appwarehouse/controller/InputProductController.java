package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.InputProduct;
import com.example.appwarehouse.service.InputProductService;
import com.example.appwarehouse.service.InputService;
import com.example.appwarehouse.transfer.InputProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public List<InputProduct> getAllInputProducts() {
        return inputProductService.getAllInputProducts();
    }

    @GetMapping("/{id}")
    public InputProduct getInputProductById(@PathVariable Integer id) {
        return inputProductService.getInputProductById(id);
    }

    @GetMapping("/byInputId/{input_id}")
    public List<InputProduct> getInputProductByInputId(@PathVariable Integer input_id) {
        return inputProductService.getInputProductByInputId(input_id);
    }

    @PostMapping
    public Result addInputProduct(@RequestBody InputProductDTO inputProductDTO) {
        return inputProductService.addInputProduct(inputProductDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id) {
        return inputProductService.deleteInputProduct(id);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody InputProductDTO inputProductDTO) {
        return inputProductService.editInputProduct(id, inputProductDTO);
    }
}
