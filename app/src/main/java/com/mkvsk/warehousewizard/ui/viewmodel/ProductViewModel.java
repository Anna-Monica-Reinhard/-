package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.repository.ProductRepository;
import com.mkvsk.warehousewizard.ui.util.SortType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;
    public MutableLiveData<Product> product;
    public MutableLiveData<List<Product>> allProducts;

    public ProductViewModel() {
        repository = new ProductRepository();
        product = new MutableLiveData<>();
        allProducts = new MutableLiveData<>();
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

    public void setAllProductsFromDB() {
        MutableLiveData<List<Product>> listProduct = new MutableLiveData<>(repository.getAllProducts());
        setAllProducts(listProduct);
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public void sortData(SortType sortType) {
        List<Product> sortedListProduct = new ArrayList<>(getAllProducts().getValue());
        switch (sortType) {
            case SORT_BY_ALPHABET_AZ:
                sortedListProduct.sort(Comparator.comparing(Product::getTitle));
                break;
            case SORT_BY_ALPHABET_ZA:
                sortedListProduct.sort(Comparator.comparing(Product::getTitle).reversed());
                break;
            case SORT_BY_CATEGORY_AZ:
                sortedListProduct.sort(Comparator.comparing(Product::getCategory));
                break;
            case SORT_BY_QTY_ASCENDING:
                sortedListProduct.sort(Comparator.comparing(Product::getQty));
                break;
            case SORT_BY_QTY_DESCENDING:
                sortedListProduct.sort(Comparator.comparing(Product::getQty).reversed());
                break;
            default:
                sortedListProduct = getAllProducts().getValue();
                break;
        }
        allProducts.setValue(sortedListProduct);
    }

    public void setAllProductsByCategory(String categoryTitle) {
        setAllProductsFromDB();
        List<Product> listProduct = new ArrayList<>(Objects.requireNonNull(allProducts.getValue()));

        List<Product> filteredList = listProduct.stream()
                .filter(product -> product.getCategory().equals(categoryTitle))
                .collect(Collectors.toList());

        setAllProducts(new MutableLiveData<>(filteredList));
    }

}



