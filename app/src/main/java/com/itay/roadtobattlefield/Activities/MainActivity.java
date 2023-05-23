package com.itay.roadtobattlefield.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.itay.roadtobattlefield.Fragments.HomeFragment;
import com.itay.roadtobattlefield.Fragments.LeaderboardFragment;
import com.itay.roadtobattlefield.R;
import com.itay.roadtobattlefield.Fragments.StatisticsFragment;
import com.itay.roadtobattlefield.Fragments.ToDoListFragment;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.TraineeLevel;
import com.itay.roadtobattlefield.TurnOffFirstyActivity;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navView;
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
    ToDoListFragment toDoListFragment = new ToDoListFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor sharedPrefEditor;

    FrameLayout frame_main;

    Boolean firsty = true;

    int bottomNavHeight = 0;

    Resources resources;

    int bottomNavView;

    float drawerLatestOffset = 0;

    public static Trainee trainee;

    public static int userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();

        firsty = sharedPreferences.getBoolean("First time in RTB app", true);

        if (firsty)
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        else if (trainee == null) {
            trainee = new Trainee("bot", "bot@.com", "05222222", TraineeLevel.Intermediate);
        }

        bottomNavView = View.VISIBLE;

        resources = getResources();
        bottomNavHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                56,
                resources.getDisplayMetrics());

        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
        materialToolbar = findViewById(R.id.topAppBar);
        bottomNavigationView = findViewById(R.id.bottomNav);


        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isOpen()) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    bottomNavView = (int) View.VISIBLE;
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                    bottomNavView = (int) View.INVISIBLE;
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.ItemHome);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ItemHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.ItemLeaderboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, leaderboardFragment).commit();
                        return true;
                    case R.id.ItemTodo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, toDoListFragment).commit();
                        return true;
                }

                return false;
            }
        });


        navView.setCheckedItem(R.id.ItemMain);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ItemAbout:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.ItemSettings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.ItemStatistics:
                        getSupportFragmentManager().beginTransaction().remove(homeFragment).commit();
                        getSupportFragmentManager().beginTransaction().remove(toDoListFragment).commit();
                        getSupportFragmentManager().beginTransaction().remove(leaderboardFragment).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, statisticsFragment).commit();
                        bottomNavView = (int) View.INVISIBLE;
                        break;
                    case R.id.ItemMain:
                        getSupportFragmentManager().beginTransaction().remove(statisticsFragment).commit();
                        bottomNavView = (int) View.VISIBLE;
                        bottomNavigationView.setSelectedItemId(R.id.ItemHome);
                        break;
                    case R.id.ItemRate:
                        startActivity(new Intent(MainActivity.this, TurnOffFirstyActivity.class));
                        break;
                    case R.id.ItemShare:
                        startActivity(new Intent(MainActivity.this, TrainingActivity.class));
                        break;
                    case R.id.ItemPrivacy:
                        Toast.makeText(MainActivity.this, "Privacy", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ItemExit:
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setTitle("Exit")
                                .setMessage("Are you sure you want to exit?")
                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .show();
                        break;
                    case R.id.ItemContactUs:
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                bottomNavigationView.setVisibility(bottomNavView);
                return true;
            }
        });


        frame_main = findViewById(R.id.container);
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(0, 0, 0, bottomNavHeight);

        frame_main.setLayoutParams(params);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {


                if (homeFragment.isVisible() || toDoListFragment.isVisible() || leaderboardFragment.isVisible()) {
                    if (slideOffset - drawerLatestOffset < 0)
                        bottomNavView = View.VISIBLE;
                    else
                        bottomNavView = View.INVISIBLE;
                } else
                    bottomNavView = View.INVISIBLE;

                bottomNavigationView.setVisibility(bottomNavView);
                drawerLatestOffset = slideOffset;

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.closeDrawer(GravityCompat.START);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }
}