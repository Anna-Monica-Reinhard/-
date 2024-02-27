package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository repository;

    public String category;
    public MutableLiveData<List<String>> allCategories = new MutableLiveData<List<String>>();

    public CategoryViewModel() {
        repository = new CategoryRepository();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String categoryTitle) {
        category = categoryTitle;
    }

    public void setAllCategories(MutableLiveData<List<String>> allCategories) {
        this.allCategories = allCategories;
    }

    public MutableLiveData<List<String>> getAllCategories() {
        return allCategories;
    }

    public void setAllCategoriesFromDB() {
        MutableLiveData<List<String>> listTitles = new MutableLiveData<>(repository.getAllCategories()
                .stream().map(Category::getTitle).collect(Collectors.toList()));
        setAllCategories(listTitles);
    }

    public MutableLiveData<List<String>> getAllCategoriesTitles() {
        return allCategories;
    }

    public void setAllCategoriesTitles(MutableLiveData<String> allCategoriesTitles) {
        this.allCategories = allCategories;
    }

    public Category getCategoryById(long value) {
        return repository.getById(value);
    }

    public void insert(Category category) {
        repository.insert(category);
        setAllCategoriesFromDB();
    }

    public void update(Category category) {
        repository.update(category);
        setAllCategoriesFromDB();
    }

    public void delete(Category value) {
        repository.delete(value);
        setAllCategoriesFromDB();
    }

    public void clearAll() {
        for (Category category : repository.getAllCategories()) {
            repository.delete(category);
        }

    }
}
