package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.ui.local.AppDatabase;
import com.mkvsk.warehousewizard.ui.local.DatabaseClient;
import com.mkvsk.warehousewizard.ui.repository.CategoryRepository;

import java.util.ArrayList;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository repository;

    public MutableLiveData<Category> category;
    public MutableLiveData<ArrayList<Category>> allCategories;

    public CategoryViewModel() {
        repository = new CategoryRepository();
    }

    //    @Contract("null -> fail")


    public Category fetchCategoryById(long value) {
        return repository.getById(value);
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void delete(Category value) {
        repository.delete(value);
    }

}
