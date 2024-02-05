package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository repository;
    public MutableLiveData<Product> product;

    public MutableLiveData<List<Product>> allProducts;

    public ProductViewModel() {
        repository = new ProductRepository();
    }

    public MutableLiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(MutableLiveData<Product> product) {
        this.product = product;
    }

    public MutableLiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(MutableLiveData<List<Product>> allProducts) {
        this.allProducts = allProducts;
    }

    public List<Product> getAllProductsFromDB(){
        return repository.getAllProducts();
    }

    public void insert(Product product) {
        repository.insert(product);
    }

}