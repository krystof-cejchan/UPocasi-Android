package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions;

/**
 * {@link CouldNotFindLocation} means that no weather data could be found for this location, or location name is invalid
 */
public class CouldNotFindLocation extends RuntimeException {
    public CouldNotFindLocation(String msg) {
        super(msg);
    }
}
