package cz.krystofcejchan.upocasi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.krystofcejchan.upocasi.R;
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.RowWeatherModule;
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.WeatherRecyclerViewAdapter;
import cz.krystofcejchan.upocasi.objects.Preferences;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.current_weather.CurrentCondition;


public class WeatherFragment extends Fragment {
    private CurrentCondition currentCondition;
    private final List<RowWeatherModule> rowWeatherModules = new ArrayList<>();

    private Preferences pf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pf = new Preferences(view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.RV_weather_data_items);

        try {
            currentCondition = new CurrentCondition(pf.getDefaultLocation());
        } catch (JSONException e) {
            Toast.makeText(this.getContext(), "Error getting the weather data", Toast.LENGTH_LONG).show();
        }
        setRowWeatherModules();
        WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter(getContext(), this.rowWeatherModules);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void setRowWeatherModules() {
        boolean celsius = pf.isCelsiusDefault();
        String temp = celsius ? " °C" : " °F";
        final DecimalFormat tempFormat = new DecimalFormat("##.0");
        final DecimalFormat humFormat = new DecimalFormat("###");
        this.rowWeatherModules.add(new RowWeatherModule("Location", currentCondition.getLocation()));
        this.rowWeatherModules.add(new RowWeatherModule("Weather condition", currentCondition.getWeatherDescription()));
        this.rowWeatherModules.add(new RowWeatherModule("Temperature",
                tempFormat.format(celsius ? currentCondition.getTemp_C() : currentCondition.getTemp_F()) + temp));
        this.rowWeatherModules.add(new RowWeatherModule("Feels-like temperature",
                tempFormat.format(celsius ? currentCondition.getFeelsLikeC() : currentCondition.getFeelsLikeF()) + temp));
        this.rowWeatherModules.add(new RowWeatherModule("Humidity", humFormat.format(currentCondition.getHumidity()) + " %"));
        this.rowWeatherModules.add(new RowWeatherModule("Precipitation", tempFormat.format(currentCondition.getPrecipMM()) + " mm"));
        this.rowWeatherModules.add(new RowWeatherModule("Cloud cover", humFormat.format(currentCondition.getCloudCover()) + " %"));
        this.rowWeatherModules.add(new RowWeatherModule("UV-index", humFormat.format(currentCondition.getUvIndex())));
        this.rowWeatherModules.add(new RowWeatherModule("Wind speed", tempFormat.format(currentCondition.getWindSpeedKmph()) + " km/h"));
    }
}