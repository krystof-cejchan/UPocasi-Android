package cz.krystofcejchan.upocasi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    HomeFragment homeFragment = new HomeFragment();
    WeatherFragment weatherFragment = new WeatherFragment();
    ForecastFragment forecastFragment = new ForecastFragment();
    SettingsFragment settingsFragment = new SettingsFragment();



    @Override

    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        final int home = R.id.home, current = R.id.current, forecast = R.id.forecast, settings = R.id.settings;

        int id = item.getItemId();

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
