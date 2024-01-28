package com.mkvsk.warehousewizard.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mkvsk.warehousewizard.databinding.FragmentAuthenticationAndRegisterBinding;
import com.mkvsk.warehousewizard.Utils.Constants;

public class AuthenticationAndRegister extends Fragment {

    private FragmentAuthenticationAndRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthenticationAndRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }

    private void initViews() {

    }

    private void initListeners() {
//        binding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable != null && editable.length() + binding.countryCodePicker.getSelectedCountryCode().length() <= Constants.MAX_PHONE_LENGTH) {
////                    binding.btnCheckUser.setEnabled(true);
//                } else if (editable == null ||
//                        editable.length() + binding.countryCodePicker.getSelectedCountryCode().length() > Constants.MAX_PHONE_LENGTH) {
////                    binding.btnCheckUser.setEnabled(false);
//                } else if (editable.length() == 30) {
////                    hideKeyboard(binding.etPhoneNumber);
//                }
//            }
//        });




    }
}