package cz.krystofcejchan.upocasi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cz.krystofcejchan.upocasi.R;
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.RowWeatherModule;
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.WeatherRecyclerViewAdapter;
import cz.krystofcejchan.upocasi.objects.Preferences;
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY;
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.WeatherForecast;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.ForecastAtHour;

public class ForecastFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, ChipGroup.OnCheckedStateChangeListener {
    TextView timeShow, dayShow;
    WeatherForecast weatherForecast;
    Chip todayChip, tomorrowChip, afterTomorrowChip;
    RecyclerView recyclerView;
    private final List<RowWeatherModule> rowWeatherModules = new ArrayList<>();
    TIME pickedTime;
    DAY pickedDay;

    private Preferences preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new Preferences(view.getContext());
        recyclerView = view.findViewById(R.id.forecastRecyclerView);
        SeekBar timeSeekBar = view.findViewById(R.id.seekBar);
        ChipGroup daysChipGroup = view.findViewById(R.id.chipGroup);

        timeSeekBar.setOnSeekBarChangeListener(this);
        daysChipGroup.setOnCheckedStateChangeListener(this);
        timeShow = view.findViewById(R.id.seekBarTime);
        dayShow = view.findViewById(R.id.seekBarDate);
        todayChip = view.findViewById(R.id.chip);
        tomorrowChip = view.findViewById(R.id.chip2);
        afterTomorrowChip = view.findViewById(R.id.chip3);

        setTime(0);
        setDate(todayChip.getId());

        try {
            weatherForecast = new WeatherForecast(preferences.getDefaultLocation(), TIME.ALL, DAY.ALL);
        } catch (IOException | JSONException e) {
            Toast.makeText(this.getContext(), "Can't get forecast", Toast.LENGTH_SHORT).show();
        }

        setRecyclerViewData();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        setTime(i);
        setRecyclerViewData();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        setDate(checkedIds.get(0));
        setRecyclerViewData();
    }

    private void setTime(int i) {
        pickedTime = TIME.getAllEnumsExcept(TIME.ALL).get(i);
        LocalTime pickedLocalTime = pickedTime.getTime();
        String date = pickedLocalTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        timeShow.setText(date);
    }

    private void setDate(Integer integer) {
        LocalDate date = LocalDate.now();
        int plusDays = todayChip.getId() == integer ? 0 : tomorrowChip.getId() == integer ? 1 : 2;
        pickedDay = plusDays == 0 ? DAY.TODAY : plusDays == 1 ? DAY.TOMORROW : DAY.AFTER_TOMORROW;
        dayShow.setText(date.plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private void setRowWeatherModules(DAY day, TIME time) {
        this.rowWeatherModules.clear();
        boolean celsius = preferences.isCelsiusDefault();
        String temp = celsius ? " °C" : " °F";
        ForecastAtHour currentCondition = weatherForecast.getForecastFor(day, time);
        final DecimalFormat tempFormat = new DecimalFormat("##.0");
        final DecimalFormat humFormat = new DecimalFormat("###");
        this.rowWeatherModules.add(new RowWeatherModule("Location", weatherForecast.getLocation()));
        this.rowWeatherModules.add(new RowWeatherModule("Weather condition", currentCondition.getWeatherDescription()));
        this.rowWeatherModules.add(new RowWeatherModule("Temperature",
                tempFormat.format(celsius ? currentCondition.getTemperatureC() : currentCondition.getTemperatureF()) + temp));
        this.rowWeatherModules.add(new RowWeatherModule("Feels-like temperature",
                tempFormat.format(celsius ? currentCondition.getFeelsLikeC() : currentCondition.getFeelsLikeF()) + temp));
        this.rowWeatherModules.add(new RowWeatherModule("Humidity", humFormat.format(currentCondition.getHumidity()) + " %"));
        this.rowWeatherModules.add(new RowWeatherModule("Precipitation", tempFormat.format(currentCondition.getPrecipMM()) + " mm"));
        this.rowWeatherModules.add(new RowWeatherModule("Cloud cover", humFormat.format(currentCondition.getCloudCover()) + " %"));
        this.rowWeatherModules.add(new RowWeatherModule("UV-index", humFormat.format(currentCondition.getUvIndex())));
        this.rowWeatherModules.add(new RowWeatherModule("Wind speed", tempFormat.format(currentCondition.getWindSpeedKmph()) + " km/h"));
    }

    private void setRecyclerViewData() {
        setRowWeatherModules(this.pickedDay, this.pickedTime);
        WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter(getContext(), this.rowWeatherModules);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}