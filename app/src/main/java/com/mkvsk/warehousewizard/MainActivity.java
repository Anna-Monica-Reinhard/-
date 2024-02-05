package com.mkvsk.warehousewizard;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.mkvsk.warehousewizard.databinding.ActivityMainBinding;
import com.mkvsk.warehousewizard.ui.util.Utils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private NavController navController;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.initContext(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNavView();
    }

    private void initNavView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.navigation_auth_and_register) {
                binding.navView.setVisibility(View.GONE);
            } else {
                binding.navView.setVisibility(View.VISIBLE);
            }
        });
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public void onBackPressed() {
//        if (navController != null && navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.navigation_products) {
//            finish();
//        }
//        super.onBackPressed();
//    }
}