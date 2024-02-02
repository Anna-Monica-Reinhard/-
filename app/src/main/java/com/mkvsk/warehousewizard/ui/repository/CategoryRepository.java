package com.mkvsk.warehousewizard.ui.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.ui.local.AppDatabase;
import com.mkvsk.warehousewizard.ui.local.CategoryDao;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements CategoryDao {
    private final CategoryDao dao;
    private AppDatabase appDatabase;

    public CategoryRepository(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
        dao = appDatabase.getCategoryDao();
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
