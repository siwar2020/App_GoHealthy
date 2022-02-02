package com.example.gohealthlyapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DonateActivity extends AppCompatActivity {
    BottomNavigationView bottmNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate);
        bottmNav = findViewById(R.id.button_navigation);
        bottmNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragrement_container,new post_fragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragement = null ;
            switch(item.getItemId()){
                case R.id.nav_add :
                    selectedFragement = new post_fragment();
                    break ;
                case R.id.nav_view :
                    selectedFragement = new view_post_fragment();
                    break ;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragrement_container ,selectedFragement).commit();
            return true ;

        }

    };


}