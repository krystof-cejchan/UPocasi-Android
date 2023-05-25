package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions;

/**
 * weather data could not be accessed, check internet connection or make sure that location is correct {@link CouldNotFindLocation}
 */
public class WeatherDataNotAccessible extends RuntimeException {
    public WeatherDataNotAccessible(String msg) {
        super(msg);
    }
}
