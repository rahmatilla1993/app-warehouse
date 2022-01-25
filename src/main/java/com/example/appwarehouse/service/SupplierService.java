package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Supplier;
import com.example.appwarehouse.repository.SupplierRepository;
import com.example.appwarehouse.transfer.Result;
import com.example.appwarehouse.transfer.SupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        return optionalSupplier.orElse(null);
    }

    public Result addingSupplier(SupplierDTO supplierDTO, boolean edit, Integer id) {
        Supplier supplier = new Supplier();

        if (edit && supplierRepository.existsByIdIsNotAndPhoneNumber(id, supplierDTO.getPhoneNumber()) ||
                !edit && supplierRepository.existsByPhoneNumber(supplierDTO.getPhoneNumber())) {
            return new Result("Bunday telefon nomer bor", false);
        }

        if (edit && supplierRepository.existsByIdIsNotAndPhoneNumberAndName(id, supplierDTO.getPhoneNumber(), supplierDTO.getName()) ||
                !edit && supplierRepository.existsByNameAndPhoneNumber(supplierDTO.getName(), supplierDTO.getPhoneNumber())) {
            return new Result("Bunday ta'minotchi bor", false);
        }
        supplier.setActive(supplierDTO.isActive());
        supplier.setName(supplierDTO.getName());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        return new Result(true, supplier);
    }

    public Result addSupplier(SupplierDTO supplierDTO) {
        Result result = addingSupplier(supplierDTO, false, null);
        if (result.isSuccess()) {
            Supplier supplier = (Supplier) result.getObject();
            supplierRepository.save(supplier);
            return new Result("Ta'minotchi qo'shildi", true);
        }
        return result;
    }

    public Result deleteSupplier(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            supplierRepository.delete(optionalSupplier.get());
            return new Result("Supplier o'chirildi", true);
        }
        return new Result("Supplier topilmadi", false);
    }

    public Result editSupplier(Integer id, SupplierDTO supplierDTO) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Result result = addingSupplier(supplierDTO, true, id);
            if (result.isSuccess()) {
                Supplier editSupplier = optionalSupplier.get();
                Supplier supplier = (Supplier) result.getObject();
                editSupplier.setActive(supplier.isActive());
                editSupplier.setName(supplier.getName());
                editSupplier.setPhoneNumber(supplier.getPhoneNumber());
                supplierRepository.save(editSupplier);
                return new Result("Ta'minotchi o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Ta'minotchi topilmadi", false);
    }
}
