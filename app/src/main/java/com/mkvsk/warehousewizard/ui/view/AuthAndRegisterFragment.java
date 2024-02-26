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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.User;
import com.mkvsk.warehousewizard.databinding.FragmentAuthAndRegisterBinding;
import com.mkvsk.warehousewizard.ui.util.Utils;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

import java.util.Objects;

public class AuthAndRegisterFragment extends Fragment {

    private FragmentAuthAndRegisterBinding binding;
    private UserViewModel userViewModel;
    private String login = "";
    private String password = "";
    boolean isAuthMode = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        binding = FragmentAuthAndRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.GONE);
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
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnRegister.setVisibility(View.VISIBLE);
            isAuthMode = true;
        }
    }

    private void initViews() {

    }

    private void clearFields() {
        binding.etLogin.setText("");
        binding.etPassword.setText("");
        binding.etUsername.setText("");
        binding.etEmail.setText("");
        binding.etPhoneNumber.setText("");
        binding.etCreatePassword.setText("");
        binding.btnLogin.setEnabled(false);
        binding.btnRegister.setEnabled(false);
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
            if (!login.isBlank() && !password.isBlank()) {
                loginUser(true);
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

        binding.btnLogin.setOnClickListener(view -> {
            login = Objects.requireNonNull(binding.etLogin.getText()).toString();
            password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            loginUser(false);
        });
        binding.btnRegister.setOnClickListener(view -> registerUser());

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
                        binding.textLayoutCreatePassword.setHint("Length 6-20, !@#$%^&* allowed");
                        binding.btnRegister.setEnabled(false);
                    } else {
                        binding.textLayoutCreatePassword.setHint("Password");
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
                        binding.textLayoutPassword.setHint("Password");
                        binding.btnLogin.setEnabled(false);
                    } else {
                        binding.textLayoutPassword.setHint("Password");
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
        binding.etUsername.addTextChangedListener(new TextWatcher() {
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

    private void loginUser(boolean autologin) {
        User foundUser = userViewModel.login(login);
        if (foundUser == null) {
            Toast.makeText(requireContext(), requireContext().getString(R.string.login_error_user_not_found), Toast.LENGTH_SHORT).show();
        } else {
            if (foundUser.getPassword().equals(password)) {
                userViewModel.setCurrentUser(foundUser);
                saveUserDataToSharedPrefs(login, password);
                NavHostFragment.findNavController(this).navigate(R.id.action_go_to_products_from_auth);
            } else {
                if (autologin) {
                    login = "";
                    password = "";
                    saveUserDataToSharedPrefs("", "");
                    isAuthMode = true;
                    setAuthOrRegisterMode();
                }
                Toast.makeText(requireContext(), requireContext().getString(R.string.login_error_password_incorrect), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerUser() {
        User newUser = new User();
        newUser.setEmail(Objects.requireNonNull(binding.etEmail.getText()).toString());
        newUser.setPassword(Objects.requireNonNull(binding.etCreatePassword.getText()).toString());
        newUser.setPhoneNumber(Objects.requireNonNull(binding.etPhoneNumber.getText()).toString());
        newUser.setUsername(Objects.requireNonNull(binding.etUsername.getText()).toString());

        userViewModel.createNewUser(newUser);
        userViewModel.setCurrentUser(newUser);
        saveUserDataToSharedPrefs(login, password);
        NavHostFragment.findNavController(this).navigate(R.id.action_go_to_products_from_auth);
    }

    private void checkDataFilled() {
        if (binding.llAuth.getVisibility() == View.VISIBLE) {
            if (binding.etLogin.getText().toString().isBlank()
                    || binding.etPassword.getText().toString().isBlank()) {
                binding.btnLogin.setEnabled(false);
                binding.btnRegister.setEnabled(false);
                login = "";
                password = "";
            } else {
                binding.btnLogin.setEnabled(true);
                binding.btnRegister.setEnabled(true);
                login = binding.etEmail.getText().toString();
                password = binding.etCreatePassword.getText().toString();
            }
        }

        if (binding.llRegister.getVisibility() == View.VISIBLE) {
            if (binding.etEmail.getText().toString().isBlank()
                    || binding.etUsername.getText().toString().isBlank()
                    || binding.etCreatePassword.getText().toString().isBlank()) {
                binding.btnLogin.setEnabled(false);
                binding.btnRegister.setEnabled(false);
                login = "";
                password = "";
            } else {
                login = binding.etEmail.getText().toString();
                password = binding.etCreatePassword.getText().toString();
                binding.btnLogin.setEnabled(true);
                binding.btnRegister.setEnabled(true);
            }
        }
    }

    private boolean passwordValidate(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }

    private void handleBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }
}