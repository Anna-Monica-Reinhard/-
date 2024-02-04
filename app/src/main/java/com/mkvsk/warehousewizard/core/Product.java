package com.mkvsk.warehousewizard.core;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "product", indices = {@Index(value = "code", unique = true)})
public class Product {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    long id;
    //    @Builder.Default
    @ColumnInfo(name = "category")
    String category = "other";
    //    @Builder.Default
    @ColumnInfo(name = "title")
    String title = "Unknown";

    @ColumnInfo(name = "code")
    String code;
    //    @Builder.Default
    @ColumnInfo(name = "qty")
    long qty = 0;
    //    @Builder.Default
    @ColumnInfo(name = "image")
    String image = "https://bit.ly/3SGyDfX";
    //    @Builder.Default
    @ColumnInfo(name = "description")
    String description = "...";
    @ColumnInfo(name = "last_editor")
    String lastEditor = "No name";

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
