package com.mkvsk.warehousewizard.Model;

public class Category {
    long pid;
    String title;

    public Category(long pid, String title) {
        this.pid = pid;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                '}';
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
