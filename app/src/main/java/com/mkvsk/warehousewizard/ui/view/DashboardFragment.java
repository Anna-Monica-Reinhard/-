package com.mkvsk.warehousewizard.ui.view;

import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_PASSWORD;
import static com.mkvsk.warehousewizard.ui.util.Constants.SP_TAG_USERNAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.mkvsk.warehousewizard.R;
import com.mkvsk.warehousewizard.databinding.FragmentDashboardBinding;
import com.mkvsk.warehousewizard.ui.viewmodel.UserViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        UserViewModel userViewModel =
//                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        userViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMenu();
        initListeners();
    }

    private void setupMenu() {
        MenuHost menuHost = binding.toolbar;
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.dashboard_actionbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_logout) {
                    startMainScreen();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initListeners() {

    }

    private void logout() {
        SharedPreferences sharedPreferences = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        editor.putString(SP_TAG_USERNAME, "").apply();
        editor.putString(SP_TAG_PASSWORD, "").apply();

//        startMainScreen("", "");
    }

    private void startMainScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.navigation_auth_and_register);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}