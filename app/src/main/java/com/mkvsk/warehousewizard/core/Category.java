package com.mkvsk.warehousewizard.core;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "category", indices = {@Index(value = "title", unique = true)})

public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
//    @Builder.Default
    @ColumnInfo(name = "title")
    String title = "other";

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
