package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

/**
 * cannot be searched for all, because return type cannot store more than one object
 */
class CannotSearchForAll(msg: String?) : RuntimeException(msg)