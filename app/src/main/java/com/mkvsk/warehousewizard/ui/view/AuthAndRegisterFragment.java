package com.mkvsk.warehousewizard.ui.view;

import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_PASSWORD;
import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_USERNAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mkvsk.warehousewizard.databinding.FragmentAuthAndRegisterBinding;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

public class AuthAndRegisterFragment extends Fragment {

    private FragmentAuthAndRegisterBinding binding;
    private SharedPreferences sharedPreferences;
    UserViewModel userViewModel;
    String login = "";
    String password = "";
    boolean isAuthMode = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        getDataFromSharedPrefs();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding = FragmentAuthAndRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
        initViews();
        initListeners();
    }

    private void setAuthOrRegisterMode() {
//        "userViewModel.modeIsAuth"
        if (isAuthMode) {
            binding.llRegister.setVisibility(View.GONE);
            binding.llAuth.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
        } else {
            binding.llRegister.setVisibility(View.VISIBLE);
            binding.llAuth.setVisibility(View.GONE);
            binding.btnSignUp.setVisibility(View.GONE);
        }
    }

    private void initObservers() {
        userViewModel.getIsAuthMode().observe(getViewLifecycleOwner(),
                isAuthMode -> this.isAuthMode = isAuthMode);

//        userViewModel.getUserByEmail()
        //        productViewModel.allProducts.observe(getViewLifecycleOwner(),
//                newData -> productAdapter.setData(newData));

        userViewModel.getLogin().observe(getViewLifecycleOwner(), login ->
        {
            if (!login.isEmpty() && !login.isBlank()) {
                binding.etLogin.setText(login);
            }
        });

        userViewModel.getPassword().observe(getViewLifecycleOwner(), password -> {
            if (!password.isEmpty() && !password.isBlank()) {
                binding.etPassword.setText(password);
            }
        });


    }

    private void initViews() {

    }

    private void saveUserDataToSharedPrefs(String login, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_TAG_USERNAME, login);
        editor.putString(SP_TAG_PASSWORD, password);
        editor.apply();
    }

    private void getDataFromSharedPrefs() {
        login = sharedPreferences.getString(SP_TAG_USERNAME, "");
        password = sharedPreferences.getString(SP_TAG_USERNAME, "");

        if (!login.isBlank() && !login.isEmpty()
                && !password.isBlank() && !password.isEmpty()) {
            userViewModel.setLogin(login);
            userViewModel.setPassword(password);
        }
    }

    private void initListeners() {
        binding.btnSignUp.setOnClickListener(view -> setAuthOrRegisterMode());

    }

//    private void handleBackPressed() {
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(false) {
//            @Override
//            public void handleOnBackPressed() {
//                if (!userViewModel.getIsAuthMode) {
//                    userViewModel.setIsAuthMode(true);
//                    setAuthOrRegisterMode();
//                }
//            }
//        });
//    }

//    private void popBackStack() {
//        NavHostFragment.findNavController(this).popBackStack();
//    }
}