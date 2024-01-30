package com.mkvsk.warehousewizard.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mkvsk.warehousewizard.Model.Category;
import com.mkvsk.warehousewizard.Model.dao.CategoryDao;
import com.mkvsk.warehousewizard.Model.Product;
import com.mkvsk.warehousewizard.Model.dao.ProductDao;
import com.mkvsk.warehousewizard.Model.User;
import com.mkvsk.warehousewizard.Model.dao.UserDao;

@Database(
        version = 1,
        entities = {
                User.class,
                Category.class,
                Product.class
        }
)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    AppDatabase db = App.getInstance().getDatabase();
    UserDao userDao = db.userDao();
    CategoryDao categoryDao = db.categoryDao();
    ProductDao productDao = db.productDao();
}
