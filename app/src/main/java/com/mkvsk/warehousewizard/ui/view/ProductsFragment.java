package com.mkvsk.warehousewizard.ui.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.Scene;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.core.Category;
import com.mkvsk.warehousewizard.core.Product;
import com.mkvsk.warehousewizard.databinding.FragmentProductsBinding;
import com.mkvsk.warehousewizard.ui.util.CustomAlertDialogBuilder;
import com.mkvsk.warehousewizard.ui.util.Utils;
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
//        ProductViewModel productViewModel =
//                new ViewModelProvider(this).get(ProductViewModel.class);

        binding = FragmentProductsBinding.inflate(inflater, container, false);


//        final TextView textView = binding.textHome;
//        productViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initViewModels();
        setupMenu();
        setupAdapters();
        initViews();
        initListeners();
    }

    private void setupMenu() {
        MenuHost menuHost = binding.toolbar;
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.products_actionbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_filter) {
                    Utils.hideKeyboard(requireActivity());
                    //                       list sort
                    setSearchMode(binding.toolbar.getMenu().findItem(R.id.menu_item_filter),
                            binding.toolbar.getMenu().findItem(R.id.menu_item_search));
                } else if (menuItem.getItemId() == R.id.menu_item_search) {
                    setSearchMode(binding.toolbar.getMenu().findItem(R.id.menu_item_search),
                            binding.toolbar.getMenu().findItem(R.id.menu_item_filter));
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setSearchMode(MenuItem searchItem, MenuItem unselectItem) {
        androidx.transition.TransitionManager.go(new Scene(binding.toolbar), new AutoTransition());
        Objects.requireNonNull(binding.edittextSearch.getText()).clear();
//        searchItem.icon !!.setTint(ContextCompat.getColor(context !!, R.color.pink_a200))
//        unselectItem ?.icon !!.setTint(ContextCompat.getColor(context !!, R.color.main_text))
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

    private void initViewModels() {
//        categoryViewModel.allCategories.observe(getViewLifecycleOwner(),
//                newData -> categoryAdapter.setData(newData));

//        productViewModel.allProducts.observe(getViewLifecycleOwner(),
//                newData -> productAdapter.setData(newData));
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
        Category newCategory = new Category();
        CustomAlertDialogBuilder.cardAddNewCategory(this.requireContext(), newCategory, () -> {
//            categoryViewModel.insert(newCategory);
        });
    }

    private void addNewProduct() {
        Toast.makeText(requireContext(), "PRODUCT", Toast.LENGTH_SHORT).show();
        Product newProduct = new Product();
        CustomAlertDialogBuilder.cardAddNewProduct(this.requireContext(), newProduct, () -> {
//                productViewModel.insert(newProduct);
        });
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
//        productsViewModel.setCategory(categoryTitle);
        if (selectedItem > 3) {
            binding.rvCategory.smoothScrollToPosition(selectedItem);
        }
    }

    @Override
    public void onProductClick(Product product) {
        CustomAlertDialogBuilder.productCard(this.getContext(), product, new OnProductCardClickListener() {
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


}