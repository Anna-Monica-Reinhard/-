package com.mkvsk.warehousewizard.core;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "product", indices = {@Index(value = "code", unique = true)})
public class Product {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "category")
    String category;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "code")
    String code;
    @ColumnInfo(name = "qty")
    long qty;
    @ColumnInfo(name = "image")
    String image;
    @ColumnInfo(name = "description")
    String description;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", qty=" + qty +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
