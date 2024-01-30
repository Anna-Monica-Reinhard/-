package com.mkvsk.warehousewizard.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "category", unique = true)})

public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;
    @ColumnInfo(name = "title")
    String title;

    public Category(long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public long getPid() {
        return id;
    }

    public void setPid(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
