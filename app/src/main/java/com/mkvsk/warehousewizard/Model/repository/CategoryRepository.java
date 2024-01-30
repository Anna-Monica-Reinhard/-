package com.mkvsk.warehousewizard.Model.repository;

import androidx.lifecycle.LiveData;

import com.mkvsk.warehousewizard.Model.Category;
import com.mkvsk.warehousewizard.Model.dao.CategoryDao;

import java.util.List;

public class CategoryRepository implements CategoryDao {
    private final CategoryDao dao;

    public CategoryRepository(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Category>> getAllCategories() {
        return dao.getAllCategories();
    }

    @Override
    public Category getById(long id) {
        return dao.getById(id);
    }

    @Override
    public void insert(Category category) {
        dao.insert(category);
    }

    @Override
    public void update(Category category) {
        dao.update(category);
    }

    @Override
    public void delete(Category category) {
        dao.delete(category);
    }
}
