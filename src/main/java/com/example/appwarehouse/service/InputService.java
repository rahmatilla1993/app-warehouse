package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.*;
import com.example.appwarehouse.repository.CurrencyRepository;
import com.example.appwarehouse.repository.InputRepository;
import com.example.appwarehouse.repository.SupplierRepository;
import com.example.appwarehouse.repository.WareHouseRepository;
import com.example.appwarehouse.transfer.InputDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Input> getInputs() {
        return inputRepository.findAll();
    }

    public Input getInput(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        return optionalInput.orElse(null);
    }

    public List<Input> getInputsByWarehouseId(Integer warehouse_id) {
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(warehouse_id);
        if (optionalWareHouse.isPresent()) {
            return inputRepository.findAllByWareHouse_Id(warehouse_id);
        }
        return null;
    }

    public List<Input> getInputsBySupplierId(Integer supplier_id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplier_id);
        if (optionalSupplier.isPresent()) {
            return inputRepository.findAllBySupplier_Id(supplier_id);
        }
        return null;
    }

    public Result addingInput(InputDTO inputDTO, boolean edit, Integer id) {
        Input input = new Input();

        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(inputDTO.getWareHouse_id());
        if (!optionalWareHouse.isPresent()) {
            return new Result("Ombor mavjudmas", false);
        }
        WareHouse wareHouse = optionalWareHouse.get();
        if (!wareHouse.isActive()) {
            return new Result("Ombor active holatdamas", false);
        }

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDTO.getSupplier_id());
        if (!optionalSupplier.isPresent()) {
            return new Result("Ta'minotchi mavjudmas", false);
        }
        Supplier supplier = optionalSupplier.get();
        if (!supplier.isActive()) {
            return new Result("Ta'minotchi active holatdamas", false);
        }

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDTO.getCurrency_id());
        if (!optionalCurrency.isPresent()) {
            return new Result("Pul birligi mavjudmas", false);
        }
        Currency currency = optionalCurrency.get();
        if (!currency.isActive()) {
            return new Result("Pul birligi active holatdamas", false);
        }

        if (!edit && inputRepository.existsByFactureNumber(inputDTO.getFactureNumber()) ||
                edit && inputRepository.existsByIdIsNotAndFactureNumber(id, inputDTO.getFactureNumber())) {
            return new Result("Facture number bor", false);
        }

        input.setCurrency(currency);
        input.setFactureNumber(inputDTO.getFactureNumber());
        input.setWareHouse(wareHouse);
        input.setSupplier(supplier);
        input.setDate(inputDTO.getDate());
        return new Result(true, input);
    }

    //TODO Active fieldlarni tekshirish kerak
    public Result addInput(InputDTO inputDTO) {
        Result result = addingInput(inputDTO, false, null);
        if (result.isSuccess()) {
            Input input = (Input) result.getObject();
            input.setCode(generateInputCode());
            inputRepository.save(input);
            return new Result("Input saqlandi", true);
        }
        return result;
    }

    public Result editInput(Integer id, InputDTO inputDTO) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()) {
            Result result = addingInput(inputDTO, true, id);
            if (result.isSuccess()) {
                Input editInput = optionalInput.get();
                Input input = (Input) result.getObject();
                editInput.setDate(input.getDate());
                editInput.setSupplier(input.getSupplier());
                editInput.setWareHouse(input.getWareHouse());
                editInput.setCurrency(input.getCurrency());
                editInput.setFactureNumber(input.getFactureNumber());
                inputRepository.save(editInput);
                return new Result("Input o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Input topilmadi", false);
    }

    public Result deleteInput(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()) {
            inputRepository.delete(optionalInput.get());
            return new Result("Input o'chirildi", true);
        }
        return new Result("Input topilmadi", false);
    }

    private String generateInputCode() {
        List<Input> inputs = getInputs();
        int index = inputs.size();
        if (index == 0) {
            return "1";
        }
        Input input = inputs.get(index - 1);
        index = input.getId() + 1;
        return Integer.toString(index);
    }
}
