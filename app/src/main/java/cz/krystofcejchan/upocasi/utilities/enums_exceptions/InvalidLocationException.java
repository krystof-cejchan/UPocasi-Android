package cz.krystofcejchan.upocasi.utilities.enums_exceptions;

public class InvalidLocationException extends RuntimeException {
    public InvalidLocationException(String s) {
        super(s);
    }

    public InvalidLocationException() {
    }
}
