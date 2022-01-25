package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Measurement;
import com.example.appwarehouse.service.MeasurementService;
import com.example.appwarehouse.transfer.MeasurementDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurementController(@RequestBody MeasurementDTO measurementDTO){
        Result result = measurementService.addMeasurementService(measurementDTO);
        return result;
    }

    @GetMapping
    public List<Measurement> getMeasurementsController(){
        return measurementService.getMeasurementsService();
    }

    @GetMapping("/{id}")
    public Measurement getMeasurementController(@PathVariable Integer id){
        return measurementService.getMeasurementService(id);
    }

    @PutMapping("/{id}")
    public Result editMeasurementController(@PathVariable Integer id,@RequestBody MeasurementDTO measurementDTO){
        return measurementService.editMeasurementService(id,measurementDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteMeasurementController(@PathVariable Integer id){
        return measurementService.deleteMeasurementService(id);
    }
}
