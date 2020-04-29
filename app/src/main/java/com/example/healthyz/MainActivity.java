package com.example.healthyz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(navView, navController);

        myViewModel.getUserInfo().observe(this, getUserInfoObserver());
    }

    private Observer<List<Meal>> getUserInfoObserver(){
        return new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> mealList) {
                myViewModel.setUserID(mealList.get(0).getMealID());
            }
        };
    }

}
