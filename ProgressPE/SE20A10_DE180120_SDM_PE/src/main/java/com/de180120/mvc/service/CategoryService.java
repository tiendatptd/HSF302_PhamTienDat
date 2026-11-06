package com.de180120.mvc.service;

import com.de180120.mvc.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category findById(Integer id);
}
