package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

/**
 * user is forbidden to create an instance of a class; used in [UtilityClass]
 */
class CannotCreateInstance(msg: String?) : RuntimeException(msg)