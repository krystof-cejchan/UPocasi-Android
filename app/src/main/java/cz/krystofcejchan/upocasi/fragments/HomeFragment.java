package cz.krystofcejchan.upocasi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;

import cz.krystofcejchan.upocasi.R;
import cz.krystofcejchan.upocasi.objects.GreetingData;
import cz.krystofcejchan.upocasi.objects.Preferences;


public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView greetings = view.findViewById(R.id.greeting);
        TextView temp = view.findViewById(R.id.textView);
        ImageView imgView = view.findViewById(R.id.imageView);
        String greetingText = getResources().getString(R.string.greeting);
        Preferences preferences = new Preferences(view.getContext());
        try {
            GreetingData greetingData = new GreetingData(preferences.isCelsiusDefault(), preferences.getDefaultLocation());
            String formattedTemp = new DecimalFormat("##.0").format(greetingData.getTemperature());
            greetings.setText(String.format(greetingText,
                    greetingData.getDayPhrase().toString().toLowerCase(),
                    greetingData.getWeatherState(),
                    formattedTemp,
                    preferences.isCelsiusDefault() ? 'C' : 'F',
                    greetingData.getLocation()));
            imgView.setImageResource(greetingData.getImage());
            temp.setText(String.format("%s °%c", formattedTemp, 'C'));
            final int color = greetingData.getTemperature() < 7 ? R.color.cold : greetingData.getTemperature() < 20 ? R.color.warm : R.color.hot;
            temp.setTextColor(getResources().getColor(color));
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}