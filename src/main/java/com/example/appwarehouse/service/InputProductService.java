package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Input;
import com.example.appwarehouse.entity.InputProduct;
import com.example.appwarehouse.entity.Product;
import com.example.appwarehouse.repository.InputProductRepository;
import com.example.appwarehouse.repository.InputRepository;
import com.example.appwarehouse.repository.ProductRepository;
import com.example.appwarehouse.transfer.InputProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    InputRepository inputRepository;

    @Autowired
    ProductRepository productRepository;

    public List<InputProduct> getAllInputProducts() {
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProductById(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        return optionalInputProduct.orElse(null);
    }

    public List<InputProduct> getInputProductByInputId(Integer input_id) {
        Optional<Input> optionalInput = inputRepository.findById(input_id);
        if (optionalInput.isPresent()) {
            return inputProductRepository.findAllByInput_Id(input_id);
        }
        return null;
    }

    public Result addingInputProduct(InputProductDTO inputProductDTO) {
        InputProduct inputProduct = new InputProduct();

        Optional<Product> optionalProduct = productRepository.findById(inputProductDTO.getProduct_id());
        if (!optionalProduct.isPresent()) {
            return new Result("Bunday product yo'q", false);
        }
        Product product = optionalProduct.get();
        if (!product.isActive()) {
            return new Result("Product active bo'lmagan", false);
        }

        Optional<Input> optionalInput = inputRepository.findById(inputProductDTO.getInput_id());
        if (!optionalInput.isPresent()) {
            return new Result("Bunday kirim bo'lmagan", false);
        }

        inputProduct.setProduct(product);
        inputProduct.setInput(optionalInput.get());
        inputProduct.setAmount(inputProductDTO.getAmount());
        inputProduct.setPrice(inputProductDTO.getPrice());
        inputProduct.setExpireDate(inputProductDTO.getExpireDate());
        return new Result(true, inputProduct);
    }

    public Result addInputProduct(InputProductDTO inputProductDTO) {
        Result result = addingInputProduct(inputProductDTO);
        if (result.isSuccess()) {
            InputProduct inputProduct = (InputProduct) result.getObject();
            inputProductRepository.save(inputProduct);
            return new Result("InputProduct saqlandi", true);
        }
        return result;
    }

    public Result deleteInputProduct(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isPresent()) {
            inputProductRepository.delete(optionalInputProduct.get());
            return new Result("InputProduct o'chirildi", true);
        }
        return new Result("InputProduct topilmadi", false);
    }

    public Result editInputProduct(Integer id, InputProductDTO inputProductDTO) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isPresent()) {
            Result result = addingInputProduct(inputProductDTO);
            if (result.isSuccess()) {
                InputProduct editInputProduct = optionalInputProduct.get();
                InputProduct inputProduct = (InputProduct) result.getObject();
                editInputProduct.setProduct(inputProduct.getProduct());
                editInputProduct.setInput(inputProduct.getInput());
                editInputProduct.setAmount(inputProduct.getAmount());
                editInputProduct.setPrice(inputProduct.getPrice());
                editInputProduct.setExpireDate(inputProduct.getExpireDate());
                inputProductRepository.save(editInputProduct);
                return new Result("InputProduct o'zgartirildi", true);
            }
            return result;
        }
        return new Result("InputProduct topilmadi", false);
    }
}
