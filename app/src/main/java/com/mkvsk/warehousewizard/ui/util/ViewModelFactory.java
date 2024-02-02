package com.mkvsk.warehousewizard.ui.util;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mkvsk.warehousewizard.ui.local.AppDatabase;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final AppDatabase appDatabase;

    public ViewModelFactory(Application application) {
        this.application = application;
        this.appDatabase = AppDatabase.getDatabase(application);
    }

//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(MyViewModel.class)) {
//            return (T) new MyViewModel(application, appDatabase);
//        }
//        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
//    }
}

