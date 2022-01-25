package com.example.appwarehouse.controller;

import com.example.appwarehouse.entity.Category;
import com.example.appwarehouse.service.CategoryService;
import com.example.appwarehouse.transfer.CategoryDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.addCategory(categoryDTO);
    }

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

    @GetMapping("/byParentId/{parent_id}")
    public List<Category> getCategoriesByParentId(@PathVariable Integer parent_id){
        return categoryService.getCategoriesByParentId(parent_id);
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public Result editCategory(@PathVariable Integer id,@RequestBody CategoryDTO categoryDTO){
        return categoryService.editCategory(id,categoryDTO);
    }
}
