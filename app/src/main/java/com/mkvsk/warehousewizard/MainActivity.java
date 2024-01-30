package com.mkvsk.warehousewizard;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mkvsk.warehousewizard.Model.dao.CategoryDao;
import com.mkvsk.warehousewizard.Model.dao.ProductDao;
import com.mkvsk.warehousewizard.Model.dao.UserDao;
import com.mkvsk.warehousewizard.Model.repository.CategoryRepository;
import com.mkvsk.warehousewizard.Model.repository.ProductRepository;
import com.mkvsk.warehousewizard.Model.repository.UserRepository;
import com.mkvsk.warehousewizard.databinding.ActivityMainBinding;
import com.mkvsk.warehousewizard.local.App;
import com.mkvsk.warehousewizard.local.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding = null;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private UserDao userDao;

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private AppDatabase db = App.getInstance().getDatabase();

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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_products, R.id.navigation_dashboard)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        initDatabase();
        initViewModels();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initViews() {

    }

    private void initDatabase() {
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        productDao = db.productDao();

        userRepository = new UserRepository(userDao);
        categoryRepository = new CategoryRepository(categoryDao);
        productRepository = new ProductRepository(productRepository);

//        LiveData<List<Category>> categoryLiveData = db.categoryDao().getAllCategories();
//        categoryLiveData.observe(this, categories -> Log.d("TAG", "onChanged: "));
    }

    private void initViewModels() {

    }
}