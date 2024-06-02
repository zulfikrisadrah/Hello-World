package com.example.helloworld.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.helloworld.ApiService;
import com.example.helloworld.Model.Country;
import com.example.helloworld.Fragment.CodeFragment;
import com.example.helloworld.Fragment.DetailFragment;
import com.example.helloworld.Fragment.HomeFragment;
import com.example.helloworld.Fragment.StoreFragment;
import com.example.helloworld.QuizDbHelper;
import com.example.helloworld.R;
import com.example.helloworld.RetrofitClient;
import com.example.helloworld.Model.User;
import com.example.helloworld.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private QuizDbHelper dbHelper;
    private ApiService apiService;
    private Handler lifeHandler = new Handler();
    private Runnable lifeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        dbHelper = new QuizDbHelper(this);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        fetchCountriesAndSaveToDatabase();

        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

        lifeRunnable = new Runnable() {
            @Override
            public void run() {
                UserPreferences prefs = new UserPreferences(MainActivity.this);
                if (prefs.getLife() < 5) {
                    prefs.addLife(1);
                }
                lifeHandler.postDelayed(this, 30000);
            }
        };

        lifeHandler.postDelayed(lifeRunnable, 30000);
    }

    private void fetchCountriesAndSaveToDatabase() {
        apiService.getAllCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    List<Country> countries = response.body();
                    if (countries != null) {
                        for (Country country : countries) {
                            dbHelper.insertCountry(country);
                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
            }
        });
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_home) {
            loadFragment(new HomeFragment());

        } else if (itemId == R.id.nav_store) {
            loadFragment(new StoreFragment());

        } else if (itemId == R.id.nav_detail) {
            loadFragment(new DetailFragment());

        } else if (itemId == R.id.nav_code) {
            loadFragment(new CodeFragment());

        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (bottomNav.getSelectedItemId() == R.id.nav_home) {
            super.onBackPressed();
        } else {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeHandler.removeCallbacks(lifeRunnable);
    }
}
