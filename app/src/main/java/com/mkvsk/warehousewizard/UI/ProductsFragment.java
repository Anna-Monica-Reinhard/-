package com.mkvsk.warehousewizard.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.ViewModels.ProductsViewModel;
import com.mkvsk.warehousewizard.databinding.FragmentProductsBinding;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;
    private boolean isFabsVisible = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductsViewModel productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        productsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initViewModels();
        initViews();
        initListeners();
    }

    private void initViewModels() {

    }

    private void initViews() {
        binding.fabAdd.extend();

    }

    private void initListeners() {
        binding.fabAdd.setOnClickListener(v -> onAddClick());
        binding.fabAddCategory.setOnClickListener(view -> addNewCategory());
        binding.fabAddProduct.setOnClickListener(view -> addNewProduct());
    }

    private void addNewCategory() {
        Toast.makeText(requireContext(), "CATEGORY", Toast.LENGTH_SHORT).show();
    }

    private void addNewProduct() {
        Toast.makeText(requireContext(), "PRODUCT", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onAddClick() {
        if (!isFabsVisible) {
            binding.fabAddCategory.show();
            binding.fabAddProduct.show();
            binding.fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_close, requireContext().getTheme()));
            binding.fabAdd.shrink();
            isFabsVisible = true;
        } else {
            binding.fabAddCategory.hide();
            binding.fabAddProduct.hide();
            binding.fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_add, requireContext().getTheme()));
            binding.fabAdd.extend();
            isFabsVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}