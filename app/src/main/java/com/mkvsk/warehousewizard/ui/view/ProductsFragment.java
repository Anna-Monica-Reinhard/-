package com.mkvsk.warehousewizard.ui.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.mkvsk.warehousewizard.core.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductsFragment extends Fragment implements OnCategoryClickListener, OnProductClickListener {

    private FragmentProductsBinding binding;
    private boolean isFabGroupVisible = false;
    private final CategoryAdapter categoryAdapter = new CategoryAdapter();
    private final ProductAdapter productAdapter = new ProductAdapter();
    private final Set<Product> allProducts = new HashSet<>();
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
        setupAdapters();
        loadData();
        initObservers();
        getData();
        initViews();
        initListeners();
        handleBackPressed();
    }

    private void loadData() {
        if (!productViewModel.isDataLoaded()) {
            String TAG = "LOAD_DATA";
            User user = userViewModel.login("anna");
            if (user == null) {
                userViewModel.createNewUser(new User(5555, "anna", "123123", "anna@gmail.com", "+375291111111"));
                Log.d(TAG, "loadData: user added");
            }

            categoryViewModel.clearAll();
            Log.d(TAG, "loadData: categories cleared");
            List<Category> categories = new ArrayList<>();
            categories.add(new Category(1, "все"));
            categories.add(new Category(2, "тени для бровей"));
            categories.add(new Category(3, "маски"));
            categories.add(new Category(4, "парфюмерия"));
//            categories.add(new Category(5, "шампунь"));
            for (Category category : categories) {
                categoryViewModel.insert(category);
            }
            Log.d(TAG, "loadData: categories added");

            productViewModel.clearAll();
            Log.d(TAG, "loadData: products cleared");
            List<Product> products = new ArrayList<>();
            //тени для бровей
            products.add(
                    new Product(
                            1,
                            "тени для бровей",
                            "KIKI brow",
                            "19000177754",
                            13,
                            36,
                            "https://pcdn.goldapple.ru/p/p/19000177754/web/696d67416464318db8eb010ccb4eafullhd.webp",
                            "страна происхождения: США",
                            "anna",
                            7.13)
            );
            products.add(
                    new Product(
                            2,
                            "тени для бровей",
                            "ANASTASIA BEVERLY HILLS brow powder duo",
                            "24900800005",
                            6,
                            36,
                            "https://pcdn.goldapple.ru/p/p/24900800005/web/696d674d61696e8dbba9e2fde98fdfullhd.webp",
                            "Китай (Китайская Народная Республика)",
                            "anna",
                            104.3)
            );
            products.add(
                    new Product(
                            3,
                            "тени для бровей",
                            "ARTDECO eye brow powder",
                            "17344800001",
                            13,
                            24,
                            "https://pcdn.goldapple.ru/p/p/17344800001/web/696d674d61696e8dbebf31c8d43eafullhd.webp",
                            "Франция",
                            "anna",
                            20.51)
            );
            products.add(
                    new Product(
                            4,
                            "тени для бровей",
                            "LIMONI еyebrow shadow",
                            "19000007658",
                            5,
                            24,
                            "https://pcdn.goldapple.ru/p/p/19000007658/web/696d674d61696e8dbf19f1826728efullhd.webp",
                            "Италия",
                            "anna",
                            12.63)
            );
            products.add(
                    new Product(
                            5,
                            "тени для бровей",
                            "BELOR DESIGN color brow",
                            "19000022449",
                            1,
                            24,
                            "",
                            "Беларусь",
                            "anna",
                            14.77)
            );
            // маски
            products.add(
                    new Product(
                            6,
                            "маски",
                            "ORJENA natural moisture mask sheet - black bean",
                            "19000035571",
                            31,
                            24,
                            "https://pcdn.goldapple.ru/p/p/19000035571/web/696d674d61696e8dad7fe8ff9a212fullhd.webp",
                            "Республика Корея\n",
                            "anna",
                            3.26)
            );
            products.add(
                    new Product(
                            7,
                            "маски",
                            "URIID neroli garden",
                            "19000196292",
                            5,
                            20,
                            "https://pcdn.goldapple.ru/p/p/19000196292/web/696d674d61696e8dc331911bfcc40fullhd.webp",
                            "Республика Корея",
                            "anna",
                            93.61)
            );
            products.add(
                    new Product(
                            8,
                            "маски",
                            "COSWORKER elasticity & lifting moisture mask pack",
                            "19760320732",
                            21,
                            18,
                            "https://pcdn.goldapple.ru/p/p/19760320732/web/696d674d61696e8dad7b4379bbe8ffullhd.webp",
                            "Республика Корея",
                            "anna",
                            0)
            );
            //парфюмерия
            products.add(
                    new Product(
                            9,
                            "парфюмерия",
                            "BASTILLE rayon vert 50 мл",
                            "19000125773",
                            7,
                            0,
                            "https://pcdn.goldapple.ru/p/p/19000125773/web/696d674d61696e8dc32bc4d4840edtablet.webp",
                            "Франция",
                            "anna",
                            229.23)
            );
            products.add(
                    new Product(
                            10,
                            "парфюмерия",
                            "BASTILLE rayon vert 100 мл",
                            "19000150181",
                            4,
                            60,
                            "https://pcdn.goldapple.ru/p/p/19000150181/web/696d674d61696e8dc32bc8226105btablet.webp",
                            "Франция",
                            "anna",
                            405.47)
            );
            products.add(
                    new Product(
                            11,
                            "парфюмерия",
                            "PACO RABANNE 1 million elixir 100 мл",
                            "19000119732",
                            7,
                            72,
                            "https://pcdn.goldapple.ru/p/p/19000119732/web/696d674d61696e8dc32bc2772f2b8tablet.webp",
                            "Россия",
                            "anna",
                            355.46)
            );
            products.add(
                    new Product(
                            12,
                            "парфюмерия",
                            "STATE OF MIND creative inspiration",
                            "83670100005",
                            21,
                            72,
                            "https://pcdn.goldapple.ru/p/p/83670100005/web/696d674d61696e8dc32b9f4205ca6tablet.webp",
                            "Франция",
                            "anna",
                            583.65)
            );

//            products.add(
//                    new Product(
//                            13,
//                            "шампунь",
//                            "KLORANE a la grenade",
//                            "123456789",
//                            14,
//                            36,
//                            "https://pcdn.goldapple.ru/p/p/19000019753/web/696d674d61696e8dad7e70c02f1edfullhd.webp",
//                            "Этот шампунь разработан с целью минимизации рисков возникновения аллергических реакций",
//                            "iba",
//                            26.89)
//            );
            for (Product product : products) {
                productViewModel.insert(product);
            }
            Log.d(TAG, "loadData: products added");
            productViewModel.setDataLoaded(true);
        }
    }

    private void initObservers() {
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), products -> {
            productAdapter.setData(products);
        });
        categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), categoryAdapter::setData);
    }

    private void getData() {
        productAdapter.setData(productViewModel.getAllProducts().getValue());
    }

    private void setupMenu() {
        MenuHost menuHost = binding.toolbar;
        binding.etSearch.setTextAppearance(getContext(), R.style.mySearchTextInputStyle);
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.products_actionbar_menu, menu);
            }

            @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
                productAdapter.setData(productViewModel.getSortedProducts().getValue());
                Utils.hideKeyboard(requireActivity());
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void setupAdapters() {
        RecyclerView rvCategory = binding.rvCategory;
        categoryAdapter.setClickListener(this);
        rvCategory.setAdapter(categoryAdapter);

        RecyclerView rvProduct = binding.rvProduct;
        mRecyclerView = rvProduct;
        productAdapter.setClickListener(this);
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

    @SuppressLint("NotifyDataSetChanged")
    private void findProducts(String queryText) {
        List<Product> collect = productViewModel.getAllProducts().getValue().stream().filter(product -> product.getTitle().toLowerCase().contains(queryText.toLowerCase())).collect(Collectors.toList());

//        allProducts.addAll(Objects.requireNonNull(productViewModel.getAllProducts().getValue()));
//        Set<Product> temp = allProducts.stream()
//                .filter(it -> it.getTitle().contains(queryText))
//                .collect(Collectors.toSet());

        productAdapter.setData(collect);
        binding.rvProduct.invalidate();

//        if (temp.isEmpty()) {
//            productAdapter.setData(productViewModel.getAllProducts().getValue());
//        } else {
//            productAdapter.setData(new ArrayList<>(temp));
//            binding.rvProduct.invalidate();
//        }
    }


    private void addNewCategory() {
        Category newCategory = new Category();
        CustomAlertDialogBuilder.cardAddNewCategory(this.requireContext(), newCategory, () -> {
            categoryViewModel.insert(newCategory);
            categoryAdapter.setData(categoryViewModel.getAllCategories().getValue());
            Toast.makeText(requireContext(), "Категория добавлена", Toast.LENGTH_SHORT).show();
            binding.fabAdd.performClick();
        }).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNewProduct() {
        Product newProduct = new Product();
        String editorName = Objects.requireNonNullElse(userViewModel.getCurrentUser().getValue().username, "Unknown");
        CustomAlertDialogBuilder.cardAddNewProduct(this.requireContext(),
                editorName, newProduct, categoryViewModel.getAllCategories().getValue(), () -> {
                    productViewModel.insert(newProduct);
                    productAdapter.setData(productViewModel.getAllProducts().getValue());
                    binding.fabAdd.callOnClick();
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
        productViewModel.setAllProductsByCategory(categoryTitle);
//        if (!categoryTitle.equalsIgnoreCase("все")) {
//            categoryViewModel.setCategory(categoryTitle);
//            productViewModel.setAllProductsByCategory(categoryTitle);
//        } else {
//            productViewModel.setAllProductsByCategory("все");
//        }
    }

    @Override
    public void onProductClick(Product product, int bindingAdapterPosition) {
        CustomAlertDialogBuilder.productCardFullInfo(this.getContext(), product, new OnProductCardClickListener() {

            @Override
            public void onEdit(Product product) {
                product.setLastEditor(Objects.requireNonNull(userViewModel.getCurrentUser().getValue()).username);
                productViewModel.update(product);
                productAdapter.notifyItemChanged(bindingAdapterPosition, product);

            }

            @Override
            public void onDelete(Product product) {
                productViewModel.delete(product);
//                productAdapter.notifyItemRemoved(bindingAdapterPosition);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCloseCard() {
                productViewModel.setAllProductsFromDB();
                productAdapter.setData(productViewModel.getAllProducts().getValue());
                productAdapter.notifyDataSetChanged();
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