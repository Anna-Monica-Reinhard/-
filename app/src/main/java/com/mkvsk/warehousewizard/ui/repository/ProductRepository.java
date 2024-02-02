package com.mkvsk.warehousewizard.ui.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.ui.local.AppDatabase;
import com.mkvsk.warehousewizard.ui.local.ProductDao;

import java.util.List;

public class ProductRepository implements ProductDao {
    private final ProductDao dao;
    private AppDatabase appDatabase;

    public ProductRepository(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
        dao = appDatabase.getProductDao();
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
    public Product getByCode(String code) {
        return dao.getByCode(code);
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
