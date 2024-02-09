package com.mkvsk.warehousewizard.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.ui.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository repository;
    private final MutableLiveData<String> login = new MutableLiveData<>("");
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.setValue(currentUser);
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

    // DB queries
    public void createNewUser(User user) {
        repository.insert(user);
    }

    public void updateUser(User user) {
        repository.update(user);
        setCurrentUser(user);
    }

}