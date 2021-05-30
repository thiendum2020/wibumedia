package com.example.wibumedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.wibumedia.Fragments.HomeFragment;
import com.example.wibumedia.Fragments.NewPostFragment;
import com.example.wibumedia.Fragments.ProfileFragment;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 1000;
    private static final String TAG = MainActivity.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animatedBottomBar = findViewById(R.id.animatedBottomBar);

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment)
                    .commit();
        }

        else {
            animatedBottomBar.selectTabById(R.id.profile, true);
        }


        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                AppCompatActivity appCompatActivity = null;
                switch (newTab.getId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.chart:
                        Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.addnew:
                        fragment = new NewPostFragment();
                        break;
                    case R.id.notification:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment)
                            .commit();
                }
                else {
                    Log.e(TAG, "Error in creating Fragment");
                }

            }
        });
    }
}