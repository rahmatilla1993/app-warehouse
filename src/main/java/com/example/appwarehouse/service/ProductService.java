package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.*;
import com.example.appwarehouse.repository.AttachmentRepository;
import com.example.appwarehouse.repository.CategoryRepository;
import com.example.appwarehouse.repository.MeasurementRepository;
import com.example.appwarehouse.repository.ProductRepository;
import com.example.appwarehouse.transfer.ProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    private Result addingProduct(ProductDTO productDTO, boolean edit, Integer id) {
        Product product = new Product();

        if (edit && productRepository.existsByIdIsNotAndNameAndCategory_Id(id, productDTO.getName(), productDTO.getCategory_id())
                || !edit && productRepository.existsByNameAndCategoryId(productDTO.getName(), productDTO.getCategory_id())) {
            return new Result("Bunday product ushbu kategoriyada mavjud", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());
        if (!optionalCategory.isPresent()) {
            return new Result("Bunday category yo'q", false);
        }
        Category category = optionalCategory.get();
        if (!category.isActive()) {
            return new Result("Category active emas boshqa category tanlang yoki categoryni active qiling", false);
        }

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getFile_id());
        if (!optionalAttachment.isPresent()) {
            return new Result("Bunday fayl yo'q", false);
        }

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDTO.getMeasurement_id());
        if (!optionalMeasurement.isPresent()) {
            return new Result("Bunday o'lchov birligi yo'q", false);
        }
        Measurement measurement = optionalMeasurement.get();
        if (!measurement.isActive()) {
            return new Result("O'lchov birligi active emas boshqa o'lchov birligi tanlang yoki o'lchov birligini active qiling", false);
        }

        product.setPhoto(optionalAttachment.get());
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setMeasurement(measurement);
        product.setActive(productDTO.isActive());
        return new Result(true, product);
    }

    //TODO Category va measurement active holatdaligini tekshirish kerak
    public Result addProduct(ProductDTO productDTO) {

        Result result = addingProduct(productDTO, false, null);
        if (result.isSuccess()) {
            Product product = (Product) result.getObject();
            product.setCode(generateProductCode());
            productRepository.save(product);
            return new Result("Product qo'shildi", true);
        }
        return result;
    }

    private String generateProductCode() {
        List<Product> products = getProducts();
        int index = products.size();
        if (index == 0) {
            return "1";
        }
        Product product = products.get(index - 1);
        index = product.getId() + 1;
        return Integer.toString(index);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public List<Product> getProductsByCategoryId(Integer category_id) {
        Optional<Category> optionalCategory = categoryRepository.findById(category_id);
        if (!optionalCategory.isPresent()) {
            return null;
        }
        return productRepository.findAllByCategory_Id(category_id);
    }

    public Result deleteProduct(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return new Result("Product o'chirildi", true);
        }
        return new Result("Product yo'q", false);
    }

    public Result editProduct(Integer id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Result result = addingProduct(productDTO, true, id);
            if (result.isSuccess()) {
                Product editingProduct = optionalProduct.get();
                Product product = (Product) result.getObject();
                editingProduct.setCategory(product.getCategory());
                editingProduct.setMeasurement(product.getMeasurement());
                editingProduct.setName(product.getName());
                editingProduct.setPhoto(product.getPhoto());
                editingProduct.setActive(product.isActive());
                productRepository.save(editingProduct);
                return new Result("Product o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Product topilmadi", false);
    }
}
