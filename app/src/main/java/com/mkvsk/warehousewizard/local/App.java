package com.mkvsk.warehousewizard.local;

import android.app.Application;

import androidx.room.Room;

import kotlin.jvm.Volatile;

public class App extends Application {
    @Volatile
    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
