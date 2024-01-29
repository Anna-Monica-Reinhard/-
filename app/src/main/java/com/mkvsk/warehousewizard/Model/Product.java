package com.mkvsk.warehousewizard.Model;

public class Product {
    long pid;
    Category category;
    String title;
    Long qty;
    String image;
    String description;

    public Product(long pid, Category category, String title, Long qty, String image, String description, boolean isAvailable) {
        this.pid = pid;
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
                "pid=" + pid +
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
