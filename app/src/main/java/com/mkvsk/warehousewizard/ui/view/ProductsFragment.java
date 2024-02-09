package com.mkvsk.warehousewizard.ui.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.mkvsk.warehousewizard.ui.util.SortType;
import com.mkvsk.warehousewizard.ui.util.Utils;
import com.mkvsk.warehousewizard.ui.view.adapters.CategoryAdapter;
import com.mkvsk.warehousewizard.ui.view.adapters.ProductAdapter;
import com.mkvsk.warehousewizard.ui.view.listeners.OnCategoryClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductCardClickListener;
import com.mkvsk.warehousewizard.ui.view.listeners.OnProductClickListener;
import com.mkvsk.warehousewizard.ui.viewmodel.CategoryViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.ProductViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.setAllProductsFromDB();

        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        categoryViewModel.setAllCategoriesFromDB();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TODO loader on
        setupMenu();
        initObservers();
        getData();
        setupAdapters();
        initViews();
        initListeners();
        handleBackPressed();
    }

    private void initObservers() {
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), items -> {
            productAdapter.setData((ArrayList<Product>) items);
            productAdapter.notifyDataSetChanged();
        });

        categoryViewModel.getAllCategoriesTitles().observe(getViewLifecycleOwner(), categoryAdapter::setData);
    }

    private void getData() {

    }

    private void setupMenu() {
        MenuHost menuHost = binding.toolbar;
        binding.etSearch.setTextAppearance(getContext(), R.style.mySearchTextInputStyle);
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.products_actionbar_menu, menu);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.miSortByAlphabetAZ) {
                    productViewModel.sortData(SortType.SORT_BY_ALPHABET_AZ);
                } else if (menuItem.getItemId() == R.id.miSortByAlphabetZA) {
                    productViewModel.sortData(SortType.SORT_BY_ALPHABET_ZA);
                } else if (menuItem.getItemId() == R.id.miSortByQtyDesc) {
                    productViewModel.sortData(SortType.SORT_BY_QTY_DESCENDING);
                } else if (menuItem.getItemId() == R.id.miSortByQtyAsc) {
                    productViewModel.sortData(SortType.SORT_BY_QTY_ASCENDING);
                } else if (menuItem.getItemId() == R.id.miSortByCategory) {
                    productViewModel.sortData(SortType.SORT_BY_CATEGORY_AZ);
                }
                productAdapter.setData((ArrayList<Product>) productViewModel.getAllProducts().getValue());
                productAdapter.notifyDataSetChanged();
                Utils.hideKeyboard(requireActivity());
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        rvProduct.getAdapter().setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

    private void initViews() {
        binding.fabAdd.extend();

//        Loader off
        // TODO: 08.02.2024
    }

    private void initListeners() {
        binding.fabAdd.setOnClickListener(v -> onAddClick());
        binding.fabAddCategory.setOnClickListener(view -> addNewCategory());
        binding.fabAddProduct.setOnClickListener(view -> addNewProduct());
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
            }

            @Override
            public void onTextChanged(CharSequence queryText, int p1, int p2, int p3) {
                if (!binding.etSearch.getText().toString().trim().isEmpty() && binding.etSearch.getText().toString().length() > 1) {
                    findProducts(binding.etSearch.getText().toString());
                } else {
                    productAdapter.setData((ArrayList<Product>) productViewModel.getAllProducts().getValue());
                }
            }

            @Override
            public void afterTextChanged(Editable p0) {
            }
        });

        binding.etSearch.setOnEditorActionListener((textView, keyCode, event) -> {
            if (((event != null ? event.getAction() : -1) == KeyEvent.KEYCODE_ENTER)
                    || keyCode == EditorInfo.IME_ACTION_DONE) {
                Utils.hideKeyboard(requireActivity());
                return true;
            }
            return false;
        });

    }

    private void findProducts(String s) {
        s.trim();
        allProducts.addAll(productViewModel.getAllProducts().getValue());
        ArrayList<Product> temp = (ArrayList<Product>) allProducts.stream().filter(it
                -> it.getTitle().contains(s)).collect(Collectors.toList());

        if (!temp.isEmpty()) {
            productAdapter.setData(temp);
            productAdapter.notifyDataSetChanged();
        }
    }

    private void addNewCategory() {
        Category newCategory = new Category();
        CustomAlertDialogBuilder.cardAddNewCategory(this.requireContext(), newCategory, () -> {
            categoryViewModel.insert(newCategory);
            Toast.makeText(requireContext(), "Category added", Toast.LENGTH_SHORT).show();
        }).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNewProduct() {
        Product newProduct = new Product();
        String editorName = Objects.requireNonNullElse(userViewModel.getCurrentUser().getValue().fullName, "Unknown");
        CustomAlertDialogBuilder.cardAddNewProduct(this.requireContext(),
                editorName, newProduct, categoryViewModel.getAllCategories().getValue(), () -> {
                    productViewModel.insert(newProduct);
                    productAdapter.setData((ArrayList<Product>) productViewModel.getAllProducts().getValue());
                    productAdapter.notifyDataSetChanged();
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

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onCategoryClick(int previousItem, int selectedItem, String categoryTitle) {
        categoryAdapter.setSelected(selectedItem);
        categoryAdapter.notifyItemChanged(previousItem);
        categoryAdapter.notifyItemChanged(selectedItem);
        if (!categoryTitle.equalsIgnoreCase("all")) {
            categoryViewModel.setCategory(categoryTitle);
            productViewModel.setAllProductsByCategory(categoryTitle);
        }

//        if (selectedItem > 4) {
//            binding.rvCategory.smoothScrollToPosition(selectedItem);
//        }
    }

    @Override
    public void onProductClick(Product product) {
        CustomAlertDialogBuilder.productCardFullInfo(this.getContext(), product, new OnProductCardClickListener() {

            @Override
            public void onEdit(Product product) {
                product.setLastEditor(userViewModel.getLogin().getValue());
                productViewModel.update(product);
            }

            @Override
            public void onDelete(Product product) {
                productViewModel.delete(product);
            }

            @Override
            public void onCloseCard() {

            }
        }).show();

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