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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.Classes.StrengthExercises.GeneralExercise;
import com.itay.roadtobattlefield.Classes.WorkoutGenerator;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.Fragments.HomeFragment;
import com.itay.roadtobattlefield.Fragments.StatisticsFragment;
import com.itay.roadtobattlefield.R;
import com.itay.roadtobattlefield.Fragments.RunningFragment;
import com.itay.roadtobattlefield.Fragments.ToDoListFragment;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.TraineeLevel;
import com.itay.roadtobattlefield.TurnOffFirstyActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    DrawerLayout drawerLayout;
    NavigationView navView;
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    ToDoListFragment toDoListFragment = new ToDoListFragment();
    RunningFragment runningFragment = new RunningFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    public static WorkoutGenerator workoutGenerator;
    public static GeneralExercise[] workoutPlan;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor sharedPrefEditor;
    public static DatabaseReference databaseReference;

    FrameLayout frame_main;

    static public Boolean firsty = true, loaded = false;

    int bottomNavHeight = 0;

    Resources resources;
    DAO dao;

    int bottomNavView;

    float drawerLatestOffset = 0;

    public static Trainee trainee;

    public static int userId = 0;

    public static boolean networkStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
        dao = new DAO(DAOtype.Trainee);
        databaseReference = dao.getDatabaseReference();

        firsty = sharedPreferences.getBoolean("First time in RTB app", true);

        if (firsty) {
            userId = 0;
            sharedPrefEditor.putInt("userId RTB", -1);
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        } else {
            loadData();
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
                    case R.id.ItemRunning:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, runningFragment).commit();
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
                        getSupportFragmentManager().beginTransaction().remove(runningFragment).commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, statisticsFragment).commit();
                        bottomNavView = (int) View.INVISIBLE;
                        break;
                    case R.id.ItemMain:
                        getSupportFragmentManager().beginTransaction().remove(statisticsFragment).commit();
                        bottomNavView = (int) View.VISIBLE;
                        bottomNavigationView.setSelectedItemId(R.id.ItemHome);
                        break;
                    case R.id.ItemFirsty:
                        startActivity(new Intent(MainActivity.this, TurnOffFirstyActivity.class));
                        break;
                    case R.id.ItemRate:
                        startActivity(new Intent(MainActivity.this, RateActivity.class));
                        break;
                    case R.id.ItemShare:
                        Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ItemPrivacy:
                        Toast.makeText(MainActivity.this, "Privacy - All the copyrights are saved to ItayTamari", Toast.LENGTH_SHORT).show();
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
                                        refreshData();
                                    }
                                })
                                .show();
                        break;
                    case R.id.ItemContactUs:
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                    case R.id.ItemFriends:
                        Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
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


                if (homeFragment.isVisible() || toDoListFragment.isVisible() || runningFragment.isVisible()) {
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
        } else {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit the app?")
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            refreshData();
                        }
                    })
                    .show();
        }
    }

    private void refreshData() {
        userId = sharedPreferences.getInt("userId RTB", 0);
        dao.updateTrainee(trainee, userId, true);
        new Thread(() -> {
            while (!dao.exitApp) {

            }
            finishAffinity();
        }).start();
    }

    private void loadData() {
        userId = sharedPreferences.getInt("userId RTB", -1);
        String objectKey = String.valueOf(userId);
        databaseReference.child(objectKey).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    trainee = dataSnapshot.getValue(Trainee.class);
                    Toast.makeText(this, "Hello " + trainee.getFullName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "did not found", Toast.LENGTH_SHORT).show();
                }
            } else {
            }
        });
    }


}