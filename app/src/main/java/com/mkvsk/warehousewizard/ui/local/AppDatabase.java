package com.mkvsk.warehousewizard.ui.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.core.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        version = 1,
        entities = {
                User.class,
                Category.class,
                Product.class
        }, exportSchema = true
)
@TypeConverters(ListConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract CategoryDao getCategoryDao();

    public abstract ProductDao getProductDao();

    private volatile static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .setTransactionExecutor(databaseWriteExecutor)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
