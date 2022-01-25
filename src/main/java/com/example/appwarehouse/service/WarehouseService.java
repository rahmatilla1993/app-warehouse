package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.WareHouse;
import com.example.appwarehouse.repository.WareHouseRepository;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    public List<WareHouse> getWarehouses() {
        return wareHouseRepository.findAll();
    }

    public WareHouse getWarehouse(Integer id) {
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        return optionalWareHouse.orElse(null);
    }

    public Result addingWarehouse(WareHouse wareHouse, boolean edit, Integer id) {
        WareHouse addingWareHouse = new WareHouse();

        if (!edit && wareHouseRepository.existsByName(wareHouse.getName()) ||
                edit && wareHouseRepository.existsByIdIsNotAndName(id, wareHouse.getName())) {
            return new Result("Ombor bor", false);
        }
        addingWareHouse.setActive(wareHouse.isActive());
        addingWareHouse.setName(wareHouse.getName());
        return new Result(true, addingWareHouse);
    }

    public Result addWarehouse(WareHouse wareHouse) {
        Result result = addingWarehouse(wareHouse, false, null);
        if (result.isSuccess()) {
            WareHouse new_wareHouse = (WareHouse) result.getObject();
            wareHouseRepository.save(new_wareHouse);
            return new Result("Ombor qo'shildi", true);
        }
        return result;
    }

    public Result deleteWarehouse(Integer id) {
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (optionalWareHouse.isPresent()) {
            wareHouseRepository.delete(optionalWareHouse.get());
            return new Result("Ombor o'chirildi", true);
        }
        return new Result("Ombor topilmadi", false);
    }

    public Result editWarehouse(Integer id, WareHouse wareHouse) {
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (optionalWareHouse.isPresent()) {
            Result result = addingWarehouse(wareHouse, true, id);
            if (result.isSuccess()) {
                WareHouse editWareHouse = optionalWareHouse.get();
                WareHouse currWareHouse = (WareHouse) result.getObject();
                editWareHouse.setName(currWareHouse.getName());
                editWareHouse.setActive(currWareHouse.isActive());
                wareHouseRepository.save(editWareHouse);
                return new Result("Ombor o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Ombor topilmadi", false);
    }
}
