package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions;

/**
 * cannot be searched for all, because return type cannot store more than one object
 */
public class CannotSearchForAll extends RuntimeException {
    public CannotSearchForAll(String msg) {
        super(msg);
    }
}
