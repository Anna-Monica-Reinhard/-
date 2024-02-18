package com.mkvsk.warehousewizard.ui.viewmodel;

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
        Set<Product> products = new HashSet<>(Objects.requireNonNull(getAllProducts().getValue()));
        switch (sortType) {
            case SORT_BY_ALPHABET_AZ ->
                    products.stream().sorted(Comparator.comparing(Product::getTitle));
            case SORT_BY_ALPHABET_ZA ->
                    products.stream().sorted(Comparator.comparing(Product::getTitle).reversed());
            case SORT_BY_CATEGORY_AZ ->
                    products.stream().sorted(Comparator.comparing(Product::getCategory));
            case SORT_BY_QTY_ASCENDING ->
                    products.stream().sorted(Comparator.comparing(Product::getQty));
            case SORT_BY_QTY_DESCENDING ->
                    products.stream().sorted(Comparator.comparing(Product::getQty).reversed());
            default -> setAllProductsFromDB();
        }
    }

    public void setAllProductsByCategory(String categoryTitle) {
        setAllProductsFromDB();
        List<Product> listProduct = new ArrayList<>(Objects.requireNonNull(allProducts.getValue()));
        List<Product> filteredList = listProduct.stream().filter(product -> product.getCategory().equals(categoryTitle)).collect(Collectors.toList());
        setAllProducts(filteredList);
    }

}



