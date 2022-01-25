package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Product;
import com.example.appwarehouse.service.ProductService;
import com.example.appwarehouse.transfer.ProductDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Result addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/byCategoryId/{category_id}")
    public List<Product> getProductsByCategoryId(@PathVariable Integer category_id) {
        return productService.getProductsByCategoryId(category_id);
    }

    @DeleteMapping("/{id}")
    public Result deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public Result editProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }
}
