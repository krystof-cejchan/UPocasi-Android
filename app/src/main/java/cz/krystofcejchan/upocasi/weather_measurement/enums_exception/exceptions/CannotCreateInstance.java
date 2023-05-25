package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions;


import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass;

/**
 * user is forbidden to create an instance of a class; used in {@link UtilityClass}
 */
public class CannotCreateInstance extends RuntimeException {
    public CannotCreateInstance(String msg) {
        super(msg);
    }
}
