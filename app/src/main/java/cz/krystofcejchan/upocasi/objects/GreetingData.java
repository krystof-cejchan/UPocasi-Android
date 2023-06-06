package cz.krystofcejchan.upocasi.objects;

import static cz.krystofcejchan.upocasi.R.drawable.cloudy;
import static cz.krystofcejchan.upocasi.R.drawable.fog;
import static cz.krystofcejchan.upocasi.R.drawable.heavy_rain;
import static cz.krystofcejchan.upocasi.R.drawable.ice;
import static cz.krystofcejchan.upocasi.R.drawable.partly_cloudy;
import static cz.krystofcejchan.upocasi.R.drawable.rainy;
import static cz.krystofcejchan.upocasi.R.drawable.snow;
import static cz.krystofcejchan.upocasi.R.drawable.snowy;
import static cz.krystofcejchan.upocasi.R.drawable.sunny;
import static cz.krystofcejchan.upocasi.R.drawable.thunderstorm;
import static cz.krystofcejchan.upocasi.R.drawable.unknown;
import static cz.krystofcejchan.upocasi.R.drawable.windy;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;

import cz.krystofcejchan.upocasi.utilities.enums_exceptions.DayPhrases;
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY;
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.WeatherObject;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.current_weather.CurrentCondition;

public final class GreetingData {
    private final DayPhrases dayPhrase;
    private final String weatherState;
    private final double temperature;
    private final String location;

    private final int image;


    public GreetingData(boolean celsius, String location) throws JSONException, IOException {
        LocalTime currentTime = LocalTime.now();
        this.dayPhrase = Arrays.stream(DayPhrases.values())
                .sorted((a, b) -> a.time.compareTo(b.time))
                .filter(phrase -> currentTime.isBefore(phrase.time) || currentTime.equals(phrase.time))
                .findFirst()
                .orElse(DayPhrases.DAY);

        this.location = location;

        final WeatherObject weatherObject = new WeatherObject(this.location, TIME.ALL, DAY.ALL);
        CurrentCondition currentCondition = weatherObject.getCurrentCondition();
        this.weatherState = currentCondition.getWeatherDescription();
        this.temperature = celsius ? currentCondition.getTemp_C() : currentCondition.getTemp_F();
        this.image = generateImage(currentCondition.getWeatherCode());
    }

    private int generateImage(final int weatherCode) {
        switch (weatherCode) {
            case 113:
                return sunny;
            case 116:
                return partly_cloudy;
            case 119:
            case 122:
                return cloudy;
            case 143:
            case 248:
            case 260:
                return fog;
            case 176:
            case 305:
            case 308:
                return heavy_rain;
            case 179:
            case 182:
                return snowy;
            case 185:
            case 263:
            case 266:
            case 293:
            case 296:
            case 299:
            case 302:
            case 353:
            case 356:
            case 359:
            case 362:
            case 365:
                return rainy;
            case 200:
            case 389:
            case 392:
            case 395:
                return thunderstorm;
            case 227:
                return windy;
            case 230:
            case 329:
            case 332:
                return snow;
            case 281:
            case 368:
            case 371:
                return snowy;
            case 284:
            case 335:
            case 338:
                return snow;
            case 320:
            case 323:
                return rainy;
            case 350:
            case 374:
            case 377:
            case 386:
                return ice;
            default:
                return unknown;
        }
    }


    public DayPhrases getDayPhrase() {
        return dayPhrase;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }

    public int getImage() {
        return image;
    }
}
