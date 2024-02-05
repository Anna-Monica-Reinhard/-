package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.ui.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private MutableLiveData<String> login;
    private MutableLiveData<String> password;
    private MutableLiveData<Boolean> isAuthMode;
    private MutableLiveData<User> currentUser;
    private MutableLiveData<User> newUser;
    private MutableLiveData<User> userByEmail;
    private MutableLiveData<List<User>> allUsers;

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(MutableLiveData<User> currentUser) {
        this.currentUser = currentUser;
    }

    public MutableLiveData<User> getNewUser() {
        return newUser;
    }

    public void setNewUser(MutableLiveData<User> newUser) {
        this.newUser = newUser;
    }

    public UserViewModel() {
        repository = new UserRepository();
    }

    public User login(String login) {
        return repository.getByEmailOrPhoneNumber(login);
    }

    // Auth and register
    public LiveData<String> getLogin() {
        return login;
    }

    public void setLogin(String value) {
        login.setValue(value);
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password.setValue(value);
    }

    public LiveData<Boolean> getIsAuthMode() {
        return isAuthMode;
    }

    public void setIsAuthMode(boolean value) {
        isAuthMode.setValue(value);
    }

    // DB queries
    public void createNewUser(User user) {
        repository.insert(user);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public void setAllUsers(LiveData<List<User>> allUsers) {
        this.allUsers.setValue(allUsers.getValue());
    }

    public User getUserByEmail(String email) {
        return repository.getByEmail(email);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return repository.getByPhoneNumber(phoneNumber);
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public void deleteUser(User user) {
        repository.delete(user);
    }

}