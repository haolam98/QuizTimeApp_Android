package com.example.quiztimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bottomNavigationView = findViewById(R.id.main_page_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        //initialize Quick Access fragement
        bottomNavigationView.setSelectedItemId(R.id.quick_access);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_page_container,new QuickAcessFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment frag = null;
            switch (menuItem.getItemId())
            {
                case R.id.quick_access:
                    frag = new QuickAcessFragment();
                    break;
                case R.id.my_account:
                    frag = new OnlineAccountFragment();
                    break;
                case R.id.about_us:
                    frag = new AboutUsFragment();
                    break;
                    default:
                        frag = new QuickAcessFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_page_container,frag).commit();
            return true;
        }
    };


}
