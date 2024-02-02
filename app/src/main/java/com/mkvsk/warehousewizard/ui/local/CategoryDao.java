package com.mkvsk.warehousewizard.ui.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.mkvsk.warehousewizard.core.Category;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    @TypeConverters(ListConverter.class)
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category WHERE id = :id")
    Category getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}

