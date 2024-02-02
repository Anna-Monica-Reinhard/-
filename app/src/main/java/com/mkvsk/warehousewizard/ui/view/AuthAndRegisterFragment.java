package com.mkvsk.warehousewizard.ui.view;

import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_PASSWORD;
import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_USERNAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.databinding.FragmentAuthAndRegisterBinding;
import com.mkvsk.warehousewizard.ui.util.Utils;

public class AuthAndRegisterFragment extends Fragment {

    private FragmentAuthAndRegisterBinding binding;
    private SharedPreferences sharedPreferences;
    //    UserViewModel userViewModel;
    String login = "";
    String password = "";
    boolean isAuthMode = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding = FragmentAuthAndRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initObservers();
        initViews();
        initListeners();
    }

    private void setAuthOrRegisterMode() {
        if (isAuthMode) {
            binding.llRegister.setVisibility(View.GONE);
            binding.llAuth.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
            binding.btnSignUp.setText(R.string.don_t_have_any_account_signup);
            binding.btnLoginRegister.setText(R.string.btn_login);
            isAuthMode = false;
        } else {
            binding.llRegister.setVisibility(View.VISIBLE);
            binding.llAuth.setVisibility(View.GONE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
            binding.btnSignUp.setText(R.string.have_any_account_sign_in);
            binding.btnLoginRegister.setText(R.string.btn_confirm_new_account);
            isAuthMode = true;
        }
        clearFields();
    }

//    private void initObservers() {
//        userViewModel.getIsAuthMode().observe(getViewLifecycleOwner(),
//                isAuthMode -> this.isAuthMode = isAuthMode);
//
////        userViewModel.getUserByEmail()
//        //        productViewModel.allProducts.observe(getViewLifecycleOwner(),
////                newData -> productAdapter.setData(newData));
//
//        userViewModel.getLogin().observe(getViewLifecycleOwner(), login ->
//        {
//            if (!login.isEmpty() && !login.isBlank()) {
//                binding.etLogin.setText(login);
//            }
//        });
//
//        userViewModel.getPassword().observe(getViewLifecycleOwner(), password -> {
//            if (!password.isEmpty() && !password.isBlank()) {
//                binding.etPassword.setText(password);
//            }
//        });
//
//
//    }

    private void initViews() {

    }

    private void clearFields() {
        binding.etLogin.setText("");
        binding.etPassword.setText("");
        binding.etName.setText("");
        binding.etEmail.setText("");
        binding.etPhoneNumber.setText("");
        binding.etCreatePassword.setText("");
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
//            userViewModel.setLogin(login);
//            userViewModel.setPassword(password);
        }
    }

    private void initListeners() {
        binding.btnSignUp.setOnClickListener(view -> {
            Utils.hideKeyboard(this.requireActivity());
            setAuthOrRegisterMode();
        });

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