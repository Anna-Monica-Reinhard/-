package com.mkvsk.warehousewizard.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
//        (indices = {@Index(value = "title", unique = true)})
public class Product {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "category")
    String category;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "qty")
    Long qty;
    @ColumnInfo(name = "image")
    String image;
    @ColumnInfo(name = "description")
    String description;

    public Product(long id, String category, String title, Long qty, String image, String description, boolean isAvailable) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.qty = qty;
        this.image = image;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", qty=" + qty +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    boolean isAvailable;
}
