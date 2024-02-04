package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.local.DatabaseClient;
import com.mkvsk.warehousewizard.ui.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository repository;
    public MutableLiveData<Product> product;

    public MutableLiveData<List<Product>> allProducts;

    public ProductViewModel() {
        repository = new ProductRepository();
    }

}