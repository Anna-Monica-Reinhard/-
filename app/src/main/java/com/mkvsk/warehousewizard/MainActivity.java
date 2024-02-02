package com.mkvsk.warehousewizard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mkvsk.warehousewizard.databinding.ActivityMainBinding;
import com.mkvsk.warehousewizard.ui.local.AppDatabase;
import com.mkvsk.warehousewizard.ui.local.CategoryDao;
import com.mkvsk.warehousewizard.ui.local.ProductDao;
import com.mkvsk.warehousewizard.ui.local.UserDao;
import com.mkvsk.warehousewizard.ui.repository.CategoryRepository;
import com.mkvsk.warehousewizard.ui.repository.ProductRepository;
import com.mkvsk.warehousewizard.ui.repository.UserRepository;
import com.mkvsk.warehousewizard.ui.viewmodel.CategoryViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.DashboardViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.ProductViewModel;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private UserDao userDao;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    //    private AppDatabase db = App.getInstance().getDatabase();
    ProductViewModel productViewModel;
    CategoryViewModel categoryViewModel;
    UserViewModel userViewModel;
    DashboardViewModel dashboardViewModel;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadSharedPreferences();
        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_products, R.id.navigation_dashboard)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
//        initDatabase();
//        initViewModels();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initViews() {

    }

    private void initDatabase() {
        appDatabase = AppDatabase.getDatabase(this);
        userRepository = new UserRepository(this);
        categoryRepository = new CategoryRepository(this);
        productRepository = new ProductRepository(this);
//        LiveData<List<Category>> categoryLiveData = db.categoryDao().getAllCategories();
//        categoryLiveData.observe(this, categories -> Log.d("TAG", "onChanged: "));
    }

    private void initViewModels() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

//        userViewModel.getLogin().observe(this, binding.etLogin::setText);
//        userViewModel.getPassword().observe(getViewLifecycleOwner(), binding.etPassword::setText);

    }

    private void loadSharedPreferences() {

    }
}