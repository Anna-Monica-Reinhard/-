package com.mkvsk.warehousewizard.Model.repository;

import androidx.lifecycle.LiveData;

import com.mkvsk.warehousewizard.Model.User;
import com.mkvsk.warehousewizard.Model.dao.UserDao;

import java.util.List;

public class UserRepository implements UserDao {
    private final UserDao dao;
    public UserRepository(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return dao.findByPhoneNumber(phoneNumber);
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
