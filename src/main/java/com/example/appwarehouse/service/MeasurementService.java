package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Measurement;
import com.example.appwarehouse.repository.MeasurementRepository;
import com.example.appwarehouse.transfer.MeasurementDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurementService(MeasurementDTO measurementDTO) {
        if (measurementRepository.existsByName(measurementDTO.getName())) {
            return new Result("O'lchov birligi mavjud", false);
        }
        Measurement measurement = new Measurement();
        measurement.setName(measurementDTO.getName());
        measurement.setActive(measurementDTO.isActive());
        measurementRepository.save(measurement);
        return new Result("O'lchov birligi qo'shildi", true);
    }

    public List<Measurement> getMeasurementsService() {
        return measurementRepository.findAll();
    }

    public Measurement getMeasurementService(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        return optionalMeasurement.orElse(null);
    }

    public Result editMeasurementService(Integer id,MeasurementDTO measurementDTO){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if(optionalMeasurement.isPresent()){
            Measurement editMeasurement = optionalMeasurement.get();
            if(measurementRepository.existsByIdIsNotAndName(id,measurementDTO.getName())){
                return new Result("O'lchov birligi mavjud",false);
            }
            editMeasurement.setName(measurementDTO.getName());
            editMeasurement.setActive(measurementDTO.isActive());
            measurementRepository.save(editMeasurement);
            return new Result("O'lchov birligi o'zgartirildi",true);
        }
        return new Result("O'lchov birligi topilmadi",false);
    }

    public Result deleteMeasurementService(Integer id){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if(optionalMeasurement.isPresent()){
            measurementRepository.delete(optionalMeasurement.get());
            return new Result("O'lchov birligi o'chrildi",true);
        }
        return new Result("O'lchov birligi topilmadi",false);
    }
}
