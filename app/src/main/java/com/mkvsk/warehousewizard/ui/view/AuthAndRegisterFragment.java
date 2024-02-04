package com.mkvsk.warehousewizard.ui.view;

import static com.mkvsk.warehousewizard.ui.util.Constants.PASSWORD_REGEX;
import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_PASSWORD;
import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_USERNAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.mkvsk.warehousewizard.MainActivity;
import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.databinding.FragmentAuthAndRegisterBinding;
import com.mkvsk.warehousewizard.ui.repository.UserRepository;
import com.mkvsk.warehousewizard.ui.util.Utils;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

public class AuthAndRegisterFragment extends Fragment {

    private FragmentAuthAndRegisterBinding binding;
    private UserViewModel userViewModel;
    String login = "";
    String password = "";
    boolean isAuthMode = true;
    private UserRepository userRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        binding = FragmentAuthAndRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initObservers();
//        userRepository = new UserRepository(getContext());
        getDataFromSharedPrefs();
        initViews();
        initListeners();
    }

    private void setAuthOrRegisterMode() {
        clearFields();
        if (isAuthMode) {
            binding.llRegister.setVisibility(View.GONE);
            binding.llAuth.setVisibility(View.VISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
            binding.btnSignUp.setText(R.string.don_t_have_any_account_signup);
            binding.btnLoginRegister.setText(R.string.btn_login);
            isAuthMode = false;
            if (!login.isEmpty() && !password.isEmpty()) {
                binding.etLogin.setText(login);
                binding.etPassword.setText(password);
            }
        } else {
            binding.llRegister.setVisibility(View.VISIBLE);
            binding.llAuth.setVisibility(View.GONE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
            binding.btnSignUp.setText(R.string.have_any_account_sign_in);
            binding.btnLoginRegister.setText(R.string.btn_confirm_new_account);
            isAuthMode = true;
        }
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
        binding.btnLoginRegister.setEnabled(false);
    }

    private void saveUserDataToSharedPrefs(String login, String password) {
        SharedPreferences sharedPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        editor.putString(SP_TAG_USERNAME, login).apply();
        editor.putString(SP_TAG_PASSWORD, password).apply();
    }

    private void getDataFromSharedPrefs() {
        try {
            SharedPreferences sharedPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
            login = sharedPreferences.getString(SP_TAG_USERNAME, "");
            password = sharedPreferences.getString(SP_TAG_PASSWORD, "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!login.isBlank() && !login.isEmpty()
                    && !password.isBlank() && !password.isEmpty()) {
//                userViewModel.setLogin(login);
//                userViewModel.setPassword(password);
                binding.etLogin.setText(login);
                binding.etPassword.setText(password);
                binding.btnLoginRegister.setEnabled(true);
            } else {
                isAuthMode = true;
                setAuthOrRegisterMode();
            }
        }
    }

    private void initListeners() {
        binding.btnSignUp.setOnClickListener(view -> {
            Utils.hideKeyboard(this.requireActivity());
            setAuthOrRegisterMode();
        });

        binding.btnLoginRegister.setOnClickListener(view -> {
//                save user to db
            User newUser = new User();
            newUser.setEmail(login);
            newUser.setPassword(password);
            userViewModel.createNewUser(newUser);
            saveUserDataToSharedPrefs(login, password);
            NavHostFragment.findNavController(this).navigate(R.id.action_go_to_products_from_auth);
        });
        binding.etCreatePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (!passwordValidate(editable.toString())) {
                        binding.textLayoutCreatePassword.setHint("Введите 6-20 символов. Разрешены !@#$%^&*");
                    } else {
                        binding.textLayoutCreatePassword.setHint("Пароль");
                        checkDataFilled();
                    }
                }
            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (!passwordValidate(editable.toString())) {
                        binding.textLayoutCreatePassword.setHint("Введите от 6 символов. Разрешены спец.символы !@#$%^&*");
                    } else {
                        binding.textLayoutCreatePassword.setHint("Пароль");
                        checkDataFilled();
                    }
                }
            }
        });
        binding.etLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkDataFilled();
            }
        });
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkDataFilled();
            }
        });
        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkDataFilled();
            }
        });
        binding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkDataFilled();
            }
        });


    }

    private void checkDataFilled() {
        if (binding.llAuth.getVisibility() == View.VISIBLE) {
            if (binding.etLogin.getText().toString().isBlank()
                    && binding.etPassword.getText().toString().isBlank()) {
                binding.btnLoginRegister.setEnabled(false);
                login = "";
                password = "";
            } else {
                binding.btnLoginRegister.setEnabled(true);
                login = binding.etEmail.getText().toString();
                password = binding.etCreatePassword.getText().toString();
            }
        }

        if (binding.llRegister.getVisibility() == View.VISIBLE) {
            if (binding.etEmail.getText().toString().isBlank()
                    && binding.etName.getText().toString().isBlank()
                    && binding.etCreatePassword.getText().toString().isBlank()) {
                binding.btnLoginRegister.setEnabled(false);
                login = "";
                password = "";
            } else {
                login = binding.etEmail.getText().toString();
                password = binding.etCreatePassword.getText().toString();
                binding.btnLoginRegister.setEnabled(true);
            }
        }
    }

    private boolean passwordValidate(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
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