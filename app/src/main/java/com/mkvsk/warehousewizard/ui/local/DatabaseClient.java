package com.mkvsk.warehousewizard.ui.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.mkvsk.warehousewizard.ui.util.Utils;

public class DatabaseClient {
    public static DatabaseClient instance;
    private AppDatabase appDatabase;
    public Context context;

    private DatabaseClient(Context context) {
        this.context = context;
        instance = this;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .createFromAsset("room_article.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DatabaseClient getInstance() {
        if (instance == null) {
            instance = new DatabaseClient(Utils.getAppContext());
        }
        return instance;
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

}
