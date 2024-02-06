package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.ui.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository repository;

    public MutableLiveData<Category> category;
    public MutableLiveData<ArrayList<Category>> allCategories;

    public MutableLiveData<String> allCategoriesTitles;

    public CategoryViewModel() {
        repository = new CategoryRepository();
    }

    public MutableLiveData<Category> getCategory() {
        return category;
    }

    public void setCategory(MutableLiveData<Category> category) {
        this.category = category;
    }

    public void setAllCategories(MutableLiveData<ArrayList<Category>> allCategories) {
        this.allCategories = allCategories;
    }

    public MutableLiveData<ArrayList<Category>> getAllCategories() {
        return allCategories;
    }

    public List<Category> getAllCategoriesFromDB() {
        return repository.getAllCategories();
    }

    public List<String> getAllCategoriesTitles() {
        List<Category> list = getAllCategoriesFromDB();
        return list.stream().map(Category::getTitle).collect(Collectors.toList());
    }

    public void setAllCategoriesTitles(MutableLiveData<String> allCategoriesTitles) {
        this.allCategoriesTitles = allCategoriesTitles;
    }

    public Category getCategoryById(long value) {
        return repository.getById(value);
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void update(Category category) {
        repository.update(category);
    }

    public void delete(Category value) {
        repository.delete(value);
    }

}
