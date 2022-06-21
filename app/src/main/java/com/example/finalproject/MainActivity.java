package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentHome fragmentHome = new FragmentHome();
        FragmentSearch fragmentSearch = new FragmentSearch();
        FragmentRegister fragmentRegister = new FragmentRegister();
        FragmentMyInfo fragmentMyInfo = new FragmentMyInfo();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.fragmentHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commitAllowingStateLoss();
                        return true;
                    case R.id.fragmentSearch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSearch).commitAllowingStateLoss();
                        return true;
                    case R.id.fragmentRegister:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentRegister).commitAllowingStateLoss();
                        return true;
                    case R.id.fragmentMyInfo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMyInfo).commitAllowingStateLoss();
                    default:
                        return true;
                }
            }
        });
    }
}