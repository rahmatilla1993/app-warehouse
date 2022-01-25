package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Input;
import com.example.appwarehouse.service.InputService;
import com.example.appwarehouse.transfer.InputDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {

    @Autowired
    InputService inputService;

    @GetMapping
    public List<Input> getInputs() {
        return inputService.getInputs();
    }

    @GetMapping("/{id}")
    public Input getInput(@PathVariable Integer id) {
        return inputService.getInput(id);
    }

    @GetMapping("/byWarehouseId/{warehouse_id}")
    public List<Input> getInputsByWarehouseId(@PathVariable Integer warehouse_id) {
        return inputService.getInputsByWarehouseId(warehouse_id);
    }

    @GetMapping("/bySupplierId/{supplier_id}")
    public List<Input> getInputsBySupplierId(@PathVariable Integer supplier_id) {
        return inputService.getInputsBySupplierId(supplier_id);
    }

    @PostMapping
    public Result addInput(@RequestBody InputDTO inputDTO) {
        return inputService.addInput(inputDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteInput(@PathVariable Integer id) {
        return inputService.deleteInput(id);
    }

    @PutMapping("/{id}")
    public Result editInput(@PathVariable Integer id,@RequestBody InputDTO inputDTO){
        return inputService.editInput(id,inputDTO);
    }
}
