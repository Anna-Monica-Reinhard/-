package com.mkvsk.warehousewizard.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.repository.ProductRepository;
import com.mkvsk.warehousewizard.ui.util.SortType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository = new ProductRepository();
    public final MutableLiveData<Product> product = new MutableLiveData<>();
    public final MutableLiveData<List<Product>> allProducts = new MutableLiveData<>();
    MutableLiveData<List<Product>> sortedProducts = new MutableLiveData<>();

    private final String TAG = "SORTED";

    public MutableLiveData<List<Product>> getSortedProducts() {
        return sortedProducts;
    }

    public void setSortedProducts(MutableLiveData<List<Product>> sortedProducts) {
        this.sortedProducts = sortedProducts;
    }

    public MutableLiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.setValue(product);
    }

    public MutableLiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts.setValue(allProducts);
    }

    public void setAllProductsFromDB() {
        setAllProducts(repository.getAllProducts());
    }

    public void insert(Product product) {
        repository.insert(product);
        setAllProductsFromDB();
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public void sortData(SortType sortType) {
        List<Product> products = new ArrayList<>(Objects.requireNonNull(getAllProducts().getValue()));
        switch (sortType) {
            case SORT_BY_ALPHABET_AZ ->
                    products =  products.stream().sorted(Comparator.comparing(Product::getTitle)).collect(Collectors.toList());
            case SORT_BY_ALPHABET_ZA ->
                    products = products.stream().sorted(Comparator.comparing(Product::getTitle).reversed()).collect(Collectors.toList());
            case SORT_BY_CATEGORY_AZ ->
                    products = products.stream().sorted(Comparator.comparing(Product::getCategory)).collect(Collectors.toList());
            case SORT_BY_QTY_ASCENDING ->
                    products = products.stream().sorted(Comparator.comparing(Product::getQty)).collect(Collectors.toList());
            case SORT_BY_QTY_DESCENDING ->
                    products = products.stream().sorted(Comparator.comparing(Product::getQty).reversed()).collect(Collectors.toList());
            default -> {
            }
        }
        Log.d(TAG, "===============================================================");
        Log.d(TAG, "sortData: " + products.stream().map(Product::getTitle).collect(Collectors.toList()));
        Log.d(TAG, "===============================================================");
        sortedProducts.setValue(products);
    }

    public void setAllProductsByCategory(String categoryTitle) {
        setAllProductsFromDB();
        List<Product> listProduct = new ArrayList<>(Objects.requireNonNull(allProducts.getValue()));
        List<Product> filteredList = listProduct.stream().filter(product -> product.getCategory().equals(categoryTitle)).collect(Collectors.toList());
        setAllProducts(filteredList);
    }

}



