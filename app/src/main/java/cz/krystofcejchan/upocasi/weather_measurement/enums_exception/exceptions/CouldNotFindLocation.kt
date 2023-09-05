package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

/**
 * [CouldNotFindLocation] means that no weather data could be found for this location, or location name is invalid
 */
class CouldNotFindLocation(msg: String?) : RuntimeException(msg)