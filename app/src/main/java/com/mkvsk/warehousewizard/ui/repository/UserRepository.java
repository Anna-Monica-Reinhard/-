package com.mkvsk.warehousewizard.ui.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.TypeConverter;

import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.ui.local.AppDatabase;
import com.mkvsk.warehousewizard.ui.local.ListConverter;
import com.mkvsk.warehousewizard.ui.local.UserDao;

import java.util.List;

public class UserRepository implements UserDao {
    private final UserDao dao;
    private AppDatabase appDatabase;

    public UserRepository(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
        dao = appDatabase.getUserDao();
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return dao.getByPhoneNumber(phoneNumber);
    }

    @Override
    public void insert(User user) {
        dao.insert(user);
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }
}
