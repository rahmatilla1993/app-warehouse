package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Category;
import com.example.appwarehouse.repository.CategoryRepository;
import com.example.appwarehouse.transfer.CategoryDTO;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result addingCategory(CategoryDTO categoryDTO, boolean edit, Integer id) {
        Category category = new Category();
        if (!edit && categoryRepository.existsByName(categoryDTO.getName()) ||
                edit && categoryRepository.existsByIdIsNotAndName(id, categoryDTO.getName())) {
            return new Result("Category mavjud", false);
        }
        if (categoryDTO.getParentCategoryId() != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (optionalParentCategory.isPresent()) {
                Category parentCategory = optionalParentCategory.get();
                category.setParentCategory(parentCategory);
            }
            else
                return new Result("Bunday parent kategoriya mavjudmas", false);
        }
        category.setActive(categoryDTO.isActive());
        category.setName(categoryDTO.getName());
        return new Result(true, category);
    }

    public Result addCategory(CategoryDTO categoryDTO) {
        Result result = addingCategory(categoryDTO, false, null);
        if (result.isSuccess()) {
            Category category = (Category) result.getObject();
            categoryRepository.save(category);
            return new Result("Category qo'shildi", true);
        }
        return result;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer category_id) {
        Optional<Category> optionalCategory = categoryRepository.findById(category_id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        return null;
    }

    public List<Category> getCategoriesByParentId(Integer parent_id) {
        if (categoryRepository.existsByParentCategory_Id(parent_id)) {
            return categoryRepository.getAllByParentCategory_Id(parent_id);
        }
        return null;
    }

    public Result deleteCategory(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return new Result("Category o'chirildi", true);
        }
        return new Result("Category topilmadi", false);
    }

    public Result editCategory(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Result result = addingCategory(categoryDTO, true, id);
            if (result.isSuccess()) {
                Category editCategory = optionalCategory.get();
                Category category = (Category) result.getObject();
                editCategory.setName(category.getName());
                editCategory.setActive(category.isActive());
                editCategory.setParentCategory(category.getParentCategory());
                categoryRepository.save(editCategory);
                return new Result("Category o'zgartirildi", true);
            }
            return result;
        }
        return new Result("Category topilmadi", false);
    }
}
