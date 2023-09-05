package cz.krystofcejchan.upocasi;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import cz.krystofcejchan.upocasi.fragments.ForecastFragment;
import cz.krystofcejchan.upocasi.fragments.HomeFragment;
import cz.krystofcejchan.upocasi.fragments.SettingsFragment;
import cz.krystofcejchan.upocasi.fragments.WeatherFragment;


/**
 * @author krystof-cejchan
 * @version 11
 */
public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;


    HomeFragment homeFragment;
    WeatherFragment weatherFragment = new WeatherFragment();
    ForecastFragment forecastFragment = new ForecastFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }


    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        } else if (id == R.id.current) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, weatherFragment)
                    .commit();
            return true;
        } else if (id == R.id.forecast) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, forecastFragment)
                    .commit();
            return true;
        } else if (id == R.id.settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, settingsFragment)
                    .commit();
            return true;
        } else return false;

    }
}
