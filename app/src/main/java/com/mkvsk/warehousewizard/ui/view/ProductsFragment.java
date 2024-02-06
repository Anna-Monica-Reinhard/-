package com.mkvsk.warehousewizard.ui.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.databinding.FragmentProductsBinding;
import com.mkvsk.warehousewizard.ui.util.CustomAlertDialogBuilder;
import com.mkvsk.warehousewizard.ui.view.adapters.CategoryAdapter;
import com.mkvsk.warehousewizard.ui.view.adapters.ProductAdapter;
import com.mkvsk.warehousewizard.ui.view.listeners.OnCategoryClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductCardClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductClickListener;
import com.mkvsk.warehousewizard.ui.viewmodel.CategoryViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ProductsFragment extends Fragment implements OnCategoryClickListener, OnProductClickListener {

    private FragmentProductsBinding binding;
    private boolean isFabGroupVisible = false;
    private final CategoryAdapter categoryAdapter = new CategoryAdapter();
    private RecyclerView rvCategory;
    private ArrayList<Category> allCategories = new ArrayList<>();
    private final ProductAdapter productAdapter = new ProductAdapter();
    private RecyclerView rvProduct;
    private ArrayList<Product> productsByCategory;
    private ArrayList<Product> allProducts = new ArrayList<>();
    private Parcelable mListState = null;
    private RecyclerView mRecyclerView = null;
    private Bundle mBundleRecyclerViewState = null;

    private final static String KEY_RECYCLER_STATE = "recycler_state";
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMenu();
        setupAdapters();
        initViews();
        initListeners();
        handleBackPressed();
    }

    private void setupMenu() {
        MenuHost menuHost = binding.toolbar;
        binding.etSearch.setTextAppearance(getContext(), R.style.mySearchTextInputStyle);
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.products_actionbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.miSortByAlphabetAZ) {
//                    Utils.hideKeyboard(requireActivity());
//                    //                       list sort
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setupAdapters() {
        rvCategory = binding.rvCategory;
//        categoryAdapter.context = this.requireContext();
        categoryAdapter.setClickListener(this::onCategoryClick);
        rvCategory.setAdapter(categoryAdapter);

        rvProduct = binding.rvProduct;
        productAdapter.context = this.requireContext();
        mRecyclerView = rvProduct;
        productAdapter.setClickListener(this::onProductClick);
        rvProduct.setAdapter(productAdapter);
        rvProduct.getAdapter().setStateRestorationPolicy(RecyclerView.Adapter
                .StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

    private void initViews() {
        binding.fabAdd.extend();
    }

    private void initListeners() {
        binding.fabAdd.setOnClickListener(v -> onAddClick());
        binding.fabAddCategory.setOnClickListener(view -> addNewCategory());
        binding.fabAddProduct.setOnClickListener(view -> addNewProduct());
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence queryText, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence queryText, int i, int i1, int i2) {
                if (queryText.length() > 2) {
                    binding.nsvSearch.smoothScrollTo(0, 0);
//                    findProduct(queryText.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable queryText) {

            }
        });
    }


    private void addNewCategory() {
        Category newCategory = new Category();
        CustomAlertDialogBuilder.cardAddNewCategory(this.requireContext(), newCategory, () -> {
            categoryViewModel.insert(newCategory);
            Toast.makeText(requireContext(), "Category added", Toast.LENGTH_SHORT).show();
        }).show();
    }

    private void addNewProduct() {
        Product newProduct = new Product();
        CustomAlertDialogBuilder.cardAddNewProduct(this.requireContext(), newProduct, categoryViewModel.getAllCategoriesTitles(), () -> {
            productViewModel.insert(newProduct);
            Toast.makeText(getContext(), "Product added", Toast.LENGTH_SHORT).show();
        }).show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onAddClick() {
        if (!isFabGroupVisible) {
            binding.fabAddCategory.show();
            binding.fabAddProduct.show();
            binding.fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_close, requireContext().getTheme()));
            binding.fabAdd.shrink();
            isFabGroupVisible = true;
        } else {
            binding.fabAddCategory.hide();
            binding.fabAddProduct.hide();
            binding.fabAdd.setIcon(getResources().getDrawable(R.drawable.ic_add, requireContext().getTheme()));
            binding.fabAdd.extend();
            isFabGroupVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCategoryClick(int previousItem, int selectedItem, String categoryTitle) {
        categoryAdapter.setSelected(selectedItem);
        categoryAdapter.notifyItemChanged(previousItem);
        categoryAdapter.notifyItemChanged(selectedItem);
//        categoryViewModel.setCategory(categoryTitle);
        if (selectedItem > 3) {
            binding.rvCategory.smoothScrollToPosition(selectedItem);
        }
    }

    @Override
    public void onProductClick(Product product) {
        CustomAlertDialogBuilder.productCardFullInfo(this.getContext(), product, new OnProductCardClickListener() {
            @Override
            public void onEditQty(boolean add) {

            }

            @Override
            public void onCloseCard() {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBundleRecyclerViewState != null) {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                new Handler(looper).post(() -> {
                    mListState = mBundleRecyclerViewState != null ? mBundleRecyclerViewState.getBundle(KEY_RECYCLER_STATE) : null;
                    if (mRecyclerView != null && mRecyclerView.getLayoutManager() != null) {
                        mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                    }
                });
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = Objects.requireNonNull(mRecyclerView.getLayoutManager()).onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
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