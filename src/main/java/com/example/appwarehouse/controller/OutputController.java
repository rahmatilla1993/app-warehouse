package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Output;
import com.example.appwarehouse.service.OutputService;
import com.example.appwarehouse.transfer.OutputDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {

    @Autowired
    OutputService outputService;

    @GetMapping
    public List<Output> getAllOutputs(){
        return outputService.getAllOutputs();
    }

    @GetMapping("/byWarehouseId/{warehouse_id}")
    public List<Output> getAllOutputsByWarehouseId(@PathVariable Integer warehouse_id){
        return outputService.getAllOutputsByWarehouseId(warehouse_id);
    }

    @GetMapping("/byClientId/{client_id}")
    public List<Output> getAllOutputsByClientId(@PathVariable Integer client_id){
        return outputService.getAllOutputsByClientId(client_id);
    }

    @PostMapping
    public Result addOutput(@RequestBody OutputDTO outputDTO){
        return outputService.addOutput(outputDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutput(@PathVariable Integer id){
        return outputService.deleteOutput(id);
    }

    @PutMapping("/{id}")
    public Result editOutput(@PathVariable Integer id,@RequestBody OutputDTO outputDTO){
        return outputService.editOutput(id,outputDTO);
    }
}
