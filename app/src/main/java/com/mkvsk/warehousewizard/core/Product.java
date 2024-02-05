package com.mkvsk.warehousewizard.core;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity(tableName = "product", indices = {@Index(value = "code", unique = true)})
public class Product {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "category")
    private String category = "";
    @ColumnInfo(name = "title")
    private String title = "";
    @ColumnInfo(name = "code")
    private String code = "";
    @ColumnInfo(name = "qty")
    private long qty = 0;
    @ColumnInfo(name = "image")
    private String image = "https://bit.ly/3SGyDfX";
    @ColumnInfo(name = "description")
    private String description = "...";
    @ColumnInfo(name = "last_editor")
    private String lastEditor = "";
    @ColumnInfo(name = "price")
    private double price = 0.0;

    public Product() {
    }

    public Product(long id, String category, String title, String code, long qty, String image, String description, String lastEditor, double price) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.code = code;
        this.qty = qty;
        this.image = image;
        this.description = description;
        this.lastEditor = lastEditor;
        this.price = price;
    }

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
                ", lastEditor='" + lastEditor + '\'' +
                ", price='" + price + '\'' +
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

    public String getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(String lastEditor) {
        this.lastEditor = lastEditor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
