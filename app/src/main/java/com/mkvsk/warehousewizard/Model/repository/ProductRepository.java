package com.mkvsk.warehousewizard.Model.repository;

import androidx.lifecycle.LiveData;

import com.mkvsk.warehousewizard.Model.Product;
import com.mkvsk.warehousewizard.Model.dao.ProductDao;

import java.util.List;

public class ProductRepository implements ProductDao {
    private final ProductDao dao;
    public ProductRepository(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Product>> getAllProducts() {
        return dao.getAllProducts();
    }

    @Override
    public Product getById(long id) {
        return dao.getById(id);
    }

    @Override
    public Product getByTitle(String title) {
        return dao.getByTitle(title);
    }

    @Override
    public void insert(Product product) {
        dao.insert(product);
    }

    @Override
    public void update(Product product) {
        dao.update(product);
    }

    @Override
    public void delete(Product product) {
        dao.delete(product);
    }
}
