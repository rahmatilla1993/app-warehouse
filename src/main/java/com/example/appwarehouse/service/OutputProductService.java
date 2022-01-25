package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Output;
import com.example.appwarehouse.entity.OutputProduct;
import com.example.appwarehouse.entity.Product;
import com.example.appwarehouse.repository.OutputProductRepository;
import com.example.appwarehouse.repository.OutputRepository;
import com.example.appwarehouse.repository.ProductRepository;
import com.example.appwarehouse.transfer.OutputProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OutputRepository outputRepository;

    public List<OutputProduct> getAllOutputProducts() {
        return outputProductRepository.findAll();
    }

    public OutputProduct getOutProduct(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        return optionalOutputProduct.orElse(null);
    }

    public List<OutputProduct> getOutputProductsByOutputId(Integer output_id) {
        Optional<Output> optionalOutput = outputRepository.findById(output_id);
        if (optionalOutput.isPresent()) {
            return outputProductRepository.getAllByOutput_Id(output_id);
        }
        return null;
    }

    public Result addingOutputProduct(OutputProductDTO outputProductDTO) {
        OutputProduct outputProduct = new OutputProduct();

        Optional<Product> optionalProduct = productRepository.findById(outputProductDTO.getProduct_id());
        if (!optionalProduct.isPresent()) {
            return new Result("Product mavjudmas", false);
        }
        Product product = optionalProduct.get();
        if (!product.isActive()) {
            return new Result("Product activemas", false);
        }

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDTO.getOutput_id());
        if (!optionalOutput.isPresent()) {
            return new Result("Mavjud bo'lmagan chiqim", false);
        }
        Output output = optionalOutput.get();

        outputProduct.setOutput(output);
        outputProduct.setProduct(product);
        outputProduct.setPrice(outputProductDTO.getPrice());
        outputProduct.setAmount(outputProductDTO.getAmount());
        return new Result(true, outputProduct);
    }

    public Result addOutputProduct(OutputProductDTO outputProductDTO) {
        Result result = addingOutputProduct(outputProductDTO);
        if (result.isSuccess()) {
            OutputProduct outputProduct = (OutputProduct) result.getObject();
            outputProductRepository.save(outputProduct);
            return new Result("Chiqim product saqlandi", true);
        }
        return result;
    }

    public Result editOutputProduct(Integer id, OutputProductDTO outputProductDTO) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isPresent()) {
            Result result = addingOutputProduct(outputProductDTO);
            if(result.isSuccess()){
                OutputProduct editOutputProduct = optionalOutputProduct.get();
                OutputProduct outputProduct = (OutputProduct) result.getObject();
                editOutputProduct.setProduct(outputProduct.getProduct());
                editOutputProduct.setOutput(outputProduct.getOutput());
                editOutputProduct.setPrice(outputProduct.getPrice());
                editOutputProduct.setAmount(outputProduct.getAmount());
                outputProductRepository.save(editOutputProduct);
                return new Result("Chiqim productlari o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Chiqim product topilmadi", false);
    }

    public Result deleteOutputProduct(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isPresent()) {
            outputProductRepository.delete(optionalOutputProduct.get());
            return new Result("Chiqim product o'chirildi", true);
        }
        return new Result("Chiqim product topilmadi", false);
    }
}
