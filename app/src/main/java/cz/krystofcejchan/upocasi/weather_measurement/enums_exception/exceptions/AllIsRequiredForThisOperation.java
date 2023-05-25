package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions;

public class AllIsRequiredForThisOperation extends RuntimeException {
    public AllIsRequiredForThisOperation(String msg) {
        super(msg);
    }
}
